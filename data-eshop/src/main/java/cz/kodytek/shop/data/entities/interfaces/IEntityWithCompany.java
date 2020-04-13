package cz.kodytek.shop.data.entities.interfaces;

import cz.kodytek.shop.data.entities.interfaces.company.ICompanyWithId;

import java.util.List;

public interface IEntityWithCompany {

    List<? extends ICompanyWithId> getCompanies();

}
