package national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.Service;

import national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.PurchaseRequestsResponses.TokenRequestDTO;
import national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.PurchaseRequestsResponses.TokenResponseDTO;
import national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.PurchaseRequestsResponses.TokenValidationDTO;
import national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.PurchasedToken;
import national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.Repository.PurchasedTokenRepository;
import national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.TokenStatus;
import national.exam.java.electricity_generating_prepaid_tokens.meter_number_management.MeterNumber;
import national.exam.java.electricity_generating_prepaid_tokens.meter_number_management.Repository.MeterNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TokenService {

    @Autowired
    private MeterNumberRepository meterNumberRepository;

    @Autowired
    private PurchasedTokenRepository purchasedTokenRepository;

    // Generate 16-digit token
    private String generateToken() {
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }

    public TokenResponseDTO buyElectricity(TokenRequestDTO dto) {
        if (dto.getAmount() < 100 || dto.getAmount() % 100 != 0) {
            throw new IllegalArgumentException("Amount must be multiple of 100 and at least 100 RwF");
        }

        // Fetch the MeterNumber by its number
        MeterNumber meterNumber = meterNumberRepository.findByNumber(dto.getMeter_number())
                .orElseThrow(() -> new IllegalArgumentException("Invalid meter number"));

        int days = (int) (dto.getAmount() / 100);
        if (days > 1825) {
            throw new IllegalArgumentException("Maximum allowed is 1825 days (5 years)");
        }

        String tokenStr = generateToken();

        PurchasedToken purchasedToken = new PurchasedToken();
        purchasedToken.setMeterNumber(meterNumber); // Now valid (MeterNumber type)
        purchasedToken.setToken(tokenStr);
        purchasedToken.setTokenStatus(TokenStatus.NEW);
        purchasedToken.setTokenValueDays(days);
        purchasedToken.setPurchasedDate(LocalDateTime.now());
        purchasedToken.setAmount(dto.getAmount());

        purchasedTokenRepository.save(purchasedToken);

        return new TokenResponseDTO(tokenStr, days);
    }

    private boolean isValidMeterNumber(String meterNumberStr) {
        if (meterNumberStr == null || meterNumberStr.length() != 6) return false;

        Optional<MeterNumber> meterNumberOpt = meterNumberRepository.findByNumber(meterNumberStr);
        return meterNumberOpt.isPresent();
    }

    public List<PurchasedToken> getTokenHistory(String meterNumberStr) {
        MeterNumber meterNumber = meterNumberRepository.findByNumber(meterNumberStr)
                .orElseThrow(() -> new IllegalArgumentException("Meter number not found"));

        return purchasedTokenRepository.findByMeterNumber(meterNumber);
    }

    public static String formatToken(String token) {
        if (token.length() != 16) {
            throw new IllegalArgumentException("Token must be 16 digits");
        }
        return token.replaceAll("(.{4})", "$1-").substring(0, 19); // e.g. 1234-5678-9012-3456
    }

    public TokenValidationDTO validateToken(String rawToken) {
        String token = rawToken.replaceAll("-", "");

        if (token.length() != 16 || !token.matches("\\d+")) {
            throw new IllegalArgumentException("Invalid token format");
        }

        PurchasedToken dbToken = purchasedTokenRepository.findByToken(token);
        if (dbToken == null) {
            throw new IllegalArgumentException("Token not found");
        }

        if (dbToken.getTokenStatus() == TokenStatus.USED) {
            throw new IllegalArgumentException("Token already used");
        }

        if (isTokenExpired(dbToken)) {
            dbToken.setTokenStatus(TokenStatus.EXPIRED);
            purchasedTokenRepository.save(dbToken);
            throw new IllegalArgumentException("Token has expired");
        }

        return new TokenValidationDTO(
                formatToken(token),
                dbToken.getTokenValueDays(),
                dbToken.getMeterNumber().getNumber(), // Get actual string value
                dbToken.getPurchasedDate(),
                dbToken.getAmount()
        );
    }

    private boolean isTokenExpired(PurchasedToken token) {
        LocalDateTime expiryDate = token.getPurchasedDate().plusDays(token.getTokenValueDays());
        return LocalDateTime.now().isAfter(expiryDate);
    }
}