package ua.r4mstein.converterlab.models.organizations;

import java.util.List;

public final class Organization {

    public String id;
    public int oldId;
    public String title;
    public String regionId;
    public String cityId;
    public String phone;
    public String address;
    public String link;

    public List<Currency> currencies;

    public static final class Currency {

        public String id;
        public String ask;
        public String bid;
    }
}
