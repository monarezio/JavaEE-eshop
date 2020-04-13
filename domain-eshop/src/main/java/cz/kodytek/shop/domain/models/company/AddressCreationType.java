package cz.kodytek.shop.domain.models.company;


public enum AddressCreationType {
    NEW("Create new address"), REUSE("Choose from existing addresses");

    private String label;

    AddressCreationType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
