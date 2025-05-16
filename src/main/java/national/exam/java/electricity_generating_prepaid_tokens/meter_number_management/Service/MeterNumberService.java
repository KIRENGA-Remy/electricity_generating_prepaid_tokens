package national.exam.java.electricity_generating_prepaid_tokens.meter_number_management.Service;

import jakarta.transaction.Transactional;
import national.exam.java.electricity_generating_prepaid_tokens.meter_number_management.MeterNumber;
import national.exam.java.electricity_generating_prepaid_tokens.meter_number_management.Repository.MeterNumberRepository;
import national.exam.java.electricity_generating_prepaid_tokens.user_registration.Repository.UserRepository;
import national.exam.java.electricity_generating_prepaid_tokens.user_registration.Role;
import national.exam.java.electricity_generating_prepaid_tokens.user_registration.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Transactional
public class MeterNumberService {
    @Autowired
    private MeterNumberRepository meterNumberRepository;
    @Autowired
    private UserRepository userRepository;

    private String generateUniqueMeterNumber(){
        String uniqueMeterNumber;
        do {
            int number = new Random().nextInt(999999);
            uniqueMeterNumber = String.format("%06d", number);
        } while (meterNumberRepository.findByNumber(uniqueMeterNumber) != null);
        return uniqueMeterNumber;
    }

    public String assignMeterToUser(Long userId){
        String currentUsername =  SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentUsername).orElseThrow(
                () -> new RuntimeException("Current user not found")
        );
        if(!currentUser.getRole().equals(Role.ADMIN)){
            throw new RuntimeException("Only admins can assign meter numbers");
        }
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        String meterNumber = generateUniqueMeterNumber();
        MeterNumber meter = new MeterNumber(meterNumber, user);
        meterNumberRepository.save(meter);
        return meterNumber;
    }

    // Get all meters assigned to a user
    public List<MeterNumber> getMetersByUserId(Long userId){
        return meterNumberRepository.findByUserId(userId);
    }
}
