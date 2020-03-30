package cz.kodytek.shop.domain.common;

import com.github.dhiraj072.randomwordgenerator.RandomWordGenerator;
import cz.kodytek.shop.data.entities.Right;
import cz.kodytek.shop.data.entities.User;
import cz.kodytek.shop.data.entities.interfaces.address.IAddressWithId;
import cz.kodytek.shop.data.entities.interfaces.user.IUserWithRights;
import cz.kodytek.shop.domain.models.address.Address;
import cz.kodytek.shop.domain.models.company.AddressCreationType;
import cz.kodytek.shop.domain.models.company.Company;
import cz.kodytek.shop.domain.models.users.LoggedInUser;
import cz.kodytek.shop.domain.models.users.RegisteredUser;
import cz.kodytek.shop.domain.services.interfaces.address.IAddressService;
import cz.kodytek.shop.domain.services.interfaces.company.ICompanyService;
import cz.kodytek.shop.domain.services.interfaces.users.IUserAuthenticationService;
import cz.kodytek.shop.domain.services.interfaces.users.IUserService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ApplicationScoped
@Named
public class DbSeeder { // TODO: Figure out a better way

    @Inject
    private IUserAuthenticationService userAuthenticationService;

    @Inject
    private IAddressService addressService;

    @Inject
    private ICompanyService companyService;

    @Inject
    private IUserService userService;

    private boolean didSeed = false;

    public void seed() {
        if (!didSeed) {
            userAuthenticationService.register(new RegisteredUser("samuel@kodytek.cz", "Samuel Kodytek", "abcabc"));
            userService.addRights(1, Right.ADMIN);

            for (int i = 0; i < 10; i++) {
                Address a = new Address();
                a.setCity(RandomWordGenerator.getRandomWord());
                a.setStreet(RandomWordGenerator.getRandomWord() + " " + (int) (Math.random() * 100));
                a.setPostCode(String.valueOf((int) (Math.random() * 100000)));

                IAddressWithId addedAddress = addressService.create(1, a);

                if (((int)((Math.random() * 10)) % 3) == 0) {
                    System.out.println("Generating company");
                    Company c = new Company();
                    c.setAddress(addedAddress);
                    c.setName(RandomWordGenerator.getRandomWord());
                    c.setIdentificationNumber((int) (Math.random() * 1000000));
                    c.setTaxIdentificationNumber("CZ" + (int) (Math.random() * 1000000));
                    c.setAddressCreationType(AddressCreationType.REUSE);
                    companyService.create(1, c);
                }
            }

            didSeed = true;
        }
    }

}
