package national.exam.java.electricity_generating_prepaid_tokens.meter_number_management.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import national.exam.java.electricity_generating_prepaid_tokens.meter_number_management.MeterNumber;
import national.exam.java.electricity_generating_prepaid_tokens.meter_number_management.Repository.MeterNumberRepository;
import national.exam.java.electricity_generating_prepaid_tokens.meter_number_management.Service.MeterNumberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meters")
@Tag(name = "Meter Management", description = "Operations related to meter number registration and management")
public class MeterNumberController {
    private MeterNumberService meterNumberService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/assign/{userId}")
    @Operation(summary = "Assign a new unique meter number to a user", responses = {
            @ApiResponse(responseCode = "200", description = "Meter number successfully assigned"),
            @ApiResponse(responseCode = "403", description = "Only admins can perform this action"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<String> assignMeter(@PathVariable Long userId){
        String meterNumber = meterNumberService.assignMeterToUser(userId);
        return ResponseEntity.ok("Assigned meter number: " +meterNumber);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all meter numbers assigned to a user", responses = {
            @ApiResponse(responseCode = "200", description = "List of meter numbers returned"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<MeterNumber>> getAllMetersForUser(@PathVariable Long userId) {
        List<MeterNumber> meters = meterNumberService.getMetersByUserId(userId);
        return ResponseEntity.ok(meters);
    }
}
