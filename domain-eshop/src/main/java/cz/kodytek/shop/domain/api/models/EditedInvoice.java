package cz.kodytek.shop.domain.api.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EditedInvoice implements Serializable {

    @NotNull(message = "Full name cannot be null.")
    @Size(min = 3, message = "Full name must contain at least 3 characters.")
    private String fullName;

    @NotNull(message = "Phone number cannot be null.")
    private String phone;

    @NotNull(message = "Email cannot be null.")
    @Email(message = "Email must be in correct format")
    private String email;

    private boolean paid;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
