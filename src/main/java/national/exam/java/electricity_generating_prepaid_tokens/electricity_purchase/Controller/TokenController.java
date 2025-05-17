package national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.PurchaseRequestsResponses.TokenRequestDTO;
import national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.PurchaseRequestsResponses.TokenResponseDTO;
import national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.PurchaseRequestsResponses.TokenValidationDTO;
import national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.PurchasedToken;
import national.exam.java.electricity_generating_prepaid_tokens.electricity_purchase.Service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tokens")
@Tag(name = "Token Purchase", description = "Generate and manage electricity tokens")
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @PostMapping("/purchase")
    @Operation(summary = "Buy electricity token", responses = {
            @ApiResponse(responseCode = "200", description = "Token generated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request (e.g., wrong amount or meter number)")
    })
    public TokenResponseDTO generateToken(@RequestBody TokenRequestDTO tokenRequestDTO) {
        return tokenService.buyElectricity(tokenRequestDTO);
    }

    @GetMapping("/history/{meterNumber}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public List<PurchasedToken> getTokenHistory(@PathVariable String meterNumber) {
        return tokenService.getTokenHistory(meterNumber);
    }

    // Validate a single token
    @GetMapping("/validate")
    @Operation(summary = "Validate token and view details", responses = {
            @ApiResponse(responseCode = "200", description = "Valid token"),
            @ApiResponse(responseCode = "400", description = "Invalid or expired token")
    })
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public TokenValidationDTO validateToken(@RequestParam String token) {
        return tokenService.validateToken(token);
    }
}
