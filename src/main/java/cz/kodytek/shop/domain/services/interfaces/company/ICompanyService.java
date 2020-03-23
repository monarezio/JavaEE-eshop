package cz.kodytek.shop.domain.services.interfaces.company;

import cz.kodytek.shop.data.entities.interfaces.company.ICompany;
import cz.kodytek.shop.data.entities.interfaces.company.ICompanyWithId;

public interface ICompanyService {

    ICompanyWithId get(long userId, long companyId);

    boolean edit(long userId, ICompanyWithId company);

    void delete(long userId, long companyId);

    ICompanyWithId create(long userId, ICompany company);

}
