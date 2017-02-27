package ua.r4mstein.converterlab.models;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainModel {


    private String sourceId;
    private String date;
    private OrgTypesBean orgTypes;
    private CurrenciesBean currencies;
    private RegionsBean regions;
    private CitiesBean cities;
    private List<OrganizationsBean> organizations;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public OrgTypesBean getOrgTypes() {
        return orgTypes;
    }

    public void setOrgTypes(OrgTypesBean orgTypes) {
        this.orgTypes = orgTypes;
    }

    public CurrenciesBean getCurrencies() {
        return currencies;
    }

    public void setCurrencies(CurrenciesBean currencies) {
        this.currencies = currencies;
    }

    public RegionsBean getRegions() {
        return regions;
    }

    public void setRegions(RegionsBean regions) {
        this.regions = regions;
    }

    public CitiesBean getCities() {
        return cities;
    }

    public void setCities(CitiesBean cities) {
        this.cities = cities;
    }

    public List<OrganizationsBean> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<OrganizationsBean> organizations) {
        this.organizations = organizations;
    }

    public static class OrgTypesBean {
        /**
         * 1 : Банки
         * 2 : Обменники
         */

        @SerializedName("1")
        private String _$1;
        @SerializedName("2")
        private String _$2;

        public String get_$1() {
            return _$1;
        }

        public void set_$1(String _$1) {
            this._$1 = _$1;
        }

        public String get_$2() {
            return _$2;
        }

        public void set_$2(String _$2) {
            this._$2 = _$2;
        }
    }

    public static class CurrenciesBean {

        private String AED;
        private String AMD;
        private String AUD;
        private String AZN;
        private String BGN;
        private String BRL;
        private String BYN;
        private String CAD;
        private String CHF;
        private String CLP;
        private String CNY;
        private String CYP;
        private String CZK;
        private String DKK;
        private String EEK;
        private String EGP;
        private String EUR;
        private String GBP;
        private String GEL;
        private String HKD;
        private String HRK;
        private String HUF;
        private String ILS;
        private String INR;
        private String ISK;
        private String JPY;
        private String KGS;
        private String KRW;
        private String KWD;
        private String KZT;
        private String LBP;
        private String LTL;
        private String LVL;
        private String MDL;
        private String MTL;
        private String MXN;
        private String NOK;
        private String NZD;
        private String PKR;
        private String PLN;
        private String RON;
        private String RUB;
        private String SAR;
        private String SEK;
        private String SGD;
        private String SKK;
        private String THB;
        private String TJS;
        private String TMT;
        private String TRY;
        private String TWD;
        private String USD;
        private String VND;

        public String getAED() {
            return AED;
        }

        public void setAED(String AED) {
            this.AED = AED;
        }

        public String getAMD() {
            return AMD;
        }

        public void setAMD(String AMD) {
            this.AMD = AMD;
        }

        public String getAUD() {
            return AUD;
        }

        public void setAUD(String AUD) {
            this.AUD = AUD;
        }

        public String getAZN() {
            return AZN;
        }

        public void setAZN(String AZN) {
            this.AZN = AZN;
        }

        public String getBGN() {
            return BGN;
        }

        public void setBGN(String BGN) {
            this.BGN = BGN;
        }

        public String getBRL() {
            return BRL;
        }

        public void setBRL(String BRL) {
            this.BRL = BRL;
        }

        public String getBYN() {
            return BYN;
        }

        public void setBYN(String BYN) {
            this.BYN = BYN;
        }

        public String getCAD() {
            return CAD;
        }

        public void setCAD(String CAD) {
            this.CAD = CAD;
        }

        public String getCHF() {
            return CHF;
        }

        public void setCHF(String CHF) {
            this.CHF = CHF;
        }

        public String getCLP() {
            return CLP;
        }

        public void setCLP(String CLP) {
            this.CLP = CLP;
        }

        public String getCNY() {
            return CNY;
        }

        public void setCNY(String CNY) {
            this.CNY = CNY;
        }

        public String getCYP() {
            return CYP;
        }

        public void setCYP(String CYP) {
            this.CYP = CYP;
        }

        public String getCZK() {
            return CZK;
        }

        public void setCZK(String CZK) {
            this.CZK = CZK;
        }

        public String getDKK() {
            return DKK;
        }

        public void setDKK(String DKK) {
            this.DKK = DKK;
        }

        public String getEEK() {
            return EEK;
        }

        public void setEEK(String EEK) {
            this.EEK = EEK;
        }

        public String getEGP() {
            return EGP;
        }

        public void setEGP(String EGP) {
            this.EGP = EGP;
        }

        public String getEUR() {
            return EUR;
        }

        public void setEUR(String EUR) {
            this.EUR = EUR;
        }

        public String getGBP() {
            return GBP;
        }

        public void setGBP(String GBP) {
            this.GBP = GBP;
        }

        public String getGEL() {
            return GEL;
        }

        public void setGEL(String GEL) {
            this.GEL = GEL;
        }

        public String getHKD() {
            return HKD;
        }

        public void setHKD(String HKD) {
            this.HKD = HKD;
        }

        public String getHRK() {
            return HRK;
        }

        public void setHRK(String HRK) {
            this.HRK = HRK;
        }

        public String getHUF() {
            return HUF;
        }

        public void setHUF(String HUF) {
            this.HUF = HUF;
        }

        public String getILS() {
            return ILS;
        }

        public void setILS(String ILS) {
            this.ILS = ILS;
        }

        public String getINR() {
            return INR;
        }

        public void setINR(String INR) {
            this.INR = INR;
        }

        public String getISK() {
            return ISK;
        }

        public void setISK(String ISK) {
            this.ISK = ISK;
        }

        public String getJPY() {
            return JPY;
        }

        public void setJPY(String JPY) {
            this.JPY = JPY;
        }

        public String getKGS() {
            return KGS;
        }

        public void setKGS(String KGS) {
            this.KGS = KGS;
        }

        public String getKRW() {
            return KRW;
        }

        public void setKRW(String KRW) {
            this.KRW = KRW;
        }

        public String getKWD() {
            return KWD;
        }

        public void setKWD(String KWD) {
            this.KWD = KWD;
        }

        public String getKZT() {
            return KZT;
        }

        public void setKZT(String KZT) {
            this.KZT = KZT;
        }

        public String getLBP() {
            return LBP;
        }

        public void setLBP(String LBP) {
            this.LBP = LBP;
        }

        public String getLTL() {
            return LTL;
        }

        public void setLTL(String LTL) {
            this.LTL = LTL;
        }

        public String getLVL() {
            return LVL;
        }

        public void setLVL(String LVL) {
            this.LVL = LVL;
        }

        public String getMDL() {
            return MDL;
        }

        public void setMDL(String MDL) {
            this.MDL = MDL;
        }

        public String getMTL() {
            return MTL;
        }

        public void setMTL(String MTL) {
            this.MTL = MTL;
        }

        public String getMXN() {
            return MXN;
        }

        public void setMXN(String MXN) {
            this.MXN = MXN;
        }

        public String getNOK() {
            return NOK;
        }

        public void setNOK(String NOK) {
            this.NOK = NOK;
        }

        public String getNZD() {
            return NZD;
        }

        public void setNZD(String NZD) {
            this.NZD = NZD;
        }

        public String getPKR() {
            return PKR;
        }

        public void setPKR(String PKR) {
            this.PKR = PKR;
        }

        public String getPLN() {
            return PLN;
        }

        public void setPLN(String PLN) {
            this.PLN = PLN;
        }

        public String getRON() {
            return RON;
        }

        public void setRON(String RON) {
            this.RON = RON;
        }

        public String getRUB() {
            return RUB;
        }

        public void setRUB(String RUB) {
            this.RUB = RUB;
        }

        public String getSAR() {
            return SAR;
        }

        public void setSAR(String SAR) {
            this.SAR = SAR;
        }

        public String getSEK() {
            return SEK;
        }

        public void setSEK(String SEK) {
            this.SEK = SEK;
        }

        public String getSGD() {
            return SGD;
        }

        public void setSGD(String SGD) {
            this.SGD = SGD;
        }

        public String getSKK() {
            return SKK;
        }

        public void setSKK(String SKK) {
            this.SKK = SKK;
        }

        public String getTHB() {
            return THB;
        }

        public void setTHB(String THB) {
            this.THB = THB;
        }

        public String getTJS() {
            return TJS;
        }

        public void setTJS(String TJS) {
            this.TJS = TJS;
        }

        public String getTMT() {
            return TMT;
        }

        public void setTMT(String TMT) {
            this.TMT = TMT;
        }

        public String getTRY() {
            return TRY;
        }

        public void setTRY(String TRY) {
            this.TRY = TRY;
        }

        public String getTWD() {
            return TWD;
        }

        public void setTWD(String TWD) {
            this.TWD = TWD;
        }

        public String getUSD() {
            return USD;
        }

        public void setUSD(String USD) {
            this.USD = USD;
        }

        public String getVND() {
            return VND;
        }

        public void setVND(String VND) {
            this.VND = VND;
        }
    }

    public static class RegionsBean {
        @SerializedName("ua,7oiylpmiow8iy1smacn")
        private String _$Ua7oiylpmiow8iy1smacn147; // FIXME check this code
        @SerializedName("ua,7oiylpmiow8iy1smac8")
        private String _$Ua7oiylpmiow8iy1smac8199; // FIXME check this code
        @SerializedName("ua,7oiylpmiow8iy1smac1")
        private String _$Ua7oiylpmiow8iy1smac145; // FIXME check this code
        @SerializedName("ua,7oiylpmiow8iy1smaci")
        private String _$Ua7oiylpmiow8iy1smaci82; // FIXME check this code
        @SerializedName("ua,7oiylpmiow8iy1smacl")
        private String _$Ua7oiylpmiow8iy1smacl133; // FIXME check this code
        @SerializedName("ua,7oiylpmiow8iy1smac7")
        private String _$Ua7oiylpmiow8iy1smac727; // FIXME check this code
        @SerializedName("ua,7oiylpmiow8iy1smabz")
        private String _$Ua7oiylpmiow8iy1smabz52; // FIXME check this code
        @SerializedName("ua,7oiylpmiow8iy1smack")
        private String _$Ua7oiylpmiow8iy1smack98; // FIXME check this code
        @SerializedName("ua,7oiylpmiow8iy1smac2")
        private String _$Ua7oiylpmiow8iy1smac2160; // FIXME check this code
        @SerializedName("ua,0,7oiylpmiow8iy1smadi")
        private String _$Ua07oiylpmiow8iy1smadi31; // FIXME check this code
        @SerializedName("ua,7oiylpmiow8iy1smac9")
        private String _$Ua7oiylpmiow8iy1smac9227; // FIXME check this code
        @SerializedName("ua,7oiylpmiow8iy1smace")
        private String _$Ua7oiylpmiow8iy1smace199; // FIXME check this code
        @SerializedName("ua,7oiylpmiow8iy1smacm")
        private String _$Ua7oiylpmiow8iy1smacm301; // FIXME check this code
        @SerializedName("ua,7oiylpmiow8iy1smac0")
        private String _$Ua7oiylpmiow8iy1smac0139; // FIXME check this code
        @SerializedName("ua,7oiylpmiow8iy1smacd")
        private String _$Ua7oiylpmiow8iy1smacd78; // FIXME check this code
        @SerializedName("ua,7oiylpmiow8iy1smacc")
        private String _$Ua7oiylpmiow8iy1smacc238; // FIXME check this code
        @SerializedName("ua,7oiylpmiow8iy1smacf")
        private String _$Ua7oiylpmiow8iy1smacf25; // FIXME check this code
        @SerializedName("ua,7oiylpmiow8iy1smac4")
        private String _$Ua7oiylpmiow8iy1smac4306; // FIXME check this code
        @SerializedName("ua,0,7oiylpmiow8iy1smadu")
        private String _$Ua07oiylpmiow8iy1smadu167; // FIXME check this code
        @SerializedName("ua,7oiylpmiow8iy1smacg")
        private String _$Ua7oiylpmiow8iy1smacg270; // FIXME check this code
        @SerializedName("ua,7oiylpmiow8iy1smac3")
        private String _$Ua7oiylpmiow8iy1smac3305; // FIXME check this code
        @SerializedName("ua,7oiylpmiow8iy1smacj")
        private String _$Ua7oiylpmiow8iy1smacj232; // FIXME check this code
        @SerializedName("ua,7oiylpmiow8iy1smach")
        private String _$Ua7oiylpmiow8iy1smach164; // FIXME check this code
        @SerializedName("ua,7oiylpmiow8iy1smac6")
        private String _$Ua7oiylpmiow8iy1smac699; // FIXME check this code
        @SerializedName("ua,7oiylpmiow8iy1smacb")
        private String _$Ua7oiylpmiow8iy1smacb254; // FIXME check this code
        @SerializedName("ua,7oiylpmiow8iy1smaca")
        private String _$Ua7oiylpmiow8iy1smaca306; // FIXME check this code
        @SerializedName("ua,7oiylpmiow8iy1smac5")
        private String _$Ua7oiylpmiow8iy1smac5167; // FIXME check this code

        public String get_$Ua7oiylpmiow8iy1smacn147() {
            return _$Ua7oiylpmiow8iy1smacn147;
        }

        public void set_$Ua7oiylpmiow8iy1smacn147(String _$Ua7oiylpmiow8iy1smacn147) {
            this._$Ua7oiylpmiow8iy1smacn147 = _$Ua7oiylpmiow8iy1smacn147;
        }

        public String get_$Ua7oiylpmiow8iy1smac8199() {
            return _$Ua7oiylpmiow8iy1smac8199;
        }

        public void set_$Ua7oiylpmiow8iy1smac8199(String _$Ua7oiylpmiow8iy1smac8199) {
            this._$Ua7oiylpmiow8iy1smac8199 = _$Ua7oiylpmiow8iy1smac8199;
        }

        public String get_$Ua7oiylpmiow8iy1smac145() {
            return _$Ua7oiylpmiow8iy1smac145;
        }

        public void set_$Ua7oiylpmiow8iy1smac145(String _$Ua7oiylpmiow8iy1smac145) {
            this._$Ua7oiylpmiow8iy1smac145 = _$Ua7oiylpmiow8iy1smac145;
        }

        public String get_$Ua7oiylpmiow8iy1smaci82() {
            return _$Ua7oiylpmiow8iy1smaci82;
        }

        public void set_$Ua7oiylpmiow8iy1smaci82(String _$Ua7oiylpmiow8iy1smaci82) {
            this._$Ua7oiylpmiow8iy1smaci82 = _$Ua7oiylpmiow8iy1smaci82;
        }

        public String get_$Ua7oiylpmiow8iy1smacl133() {
            return _$Ua7oiylpmiow8iy1smacl133;
        }

        public void set_$Ua7oiylpmiow8iy1smacl133(String _$Ua7oiylpmiow8iy1smacl133) {
            this._$Ua7oiylpmiow8iy1smacl133 = _$Ua7oiylpmiow8iy1smacl133;
        }

        public String get_$Ua7oiylpmiow8iy1smac727() {
            return _$Ua7oiylpmiow8iy1smac727;
        }

        public void set_$Ua7oiylpmiow8iy1smac727(String _$Ua7oiylpmiow8iy1smac727) {
            this._$Ua7oiylpmiow8iy1smac727 = _$Ua7oiylpmiow8iy1smac727;
        }

        public String get_$Ua7oiylpmiow8iy1smabz52() {
            return _$Ua7oiylpmiow8iy1smabz52;
        }

        public void set_$Ua7oiylpmiow8iy1smabz52(String _$Ua7oiylpmiow8iy1smabz52) {
            this._$Ua7oiylpmiow8iy1smabz52 = _$Ua7oiylpmiow8iy1smabz52;
        }

        public String get_$Ua7oiylpmiow8iy1smack98() {
            return _$Ua7oiylpmiow8iy1smack98;
        }

        public void set_$Ua7oiylpmiow8iy1smack98(String _$Ua7oiylpmiow8iy1smack98) {
            this._$Ua7oiylpmiow8iy1smack98 = _$Ua7oiylpmiow8iy1smack98;
        }

        public String get_$Ua7oiylpmiow8iy1smac2160() {
            return _$Ua7oiylpmiow8iy1smac2160;
        }

        public void set_$Ua7oiylpmiow8iy1smac2160(String _$Ua7oiylpmiow8iy1smac2160) {
            this._$Ua7oiylpmiow8iy1smac2160 = _$Ua7oiylpmiow8iy1smac2160;
        }

        public String get_$Ua07oiylpmiow8iy1smadi31() {
            return _$Ua07oiylpmiow8iy1smadi31;
        }

        public void set_$Ua07oiylpmiow8iy1smadi31(String _$Ua07oiylpmiow8iy1smadi31) {
            this._$Ua07oiylpmiow8iy1smadi31 = _$Ua07oiylpmiow8iy1smadi31;
        }

        public String get_$Ua7oiylpmiow8iy1smac9227() {
            return _$Ua7oiylpmiow8iy1smac9227;
        }

        public void set_$Ua7oiylpmiow8iy1smac9227(String _$Ua7oiylpmiow8iy1smac9227) {
            this._$Ua7oiylpmiow8iy1smac9227 = _$Ua7oiylpmiow8iy1smac9227;
        }

        public String get_$Ua7oiylpmiow8iy1smace199() {
            return _$Ua7oiylpmiow8iy1smace199;
        }

        public void set_$Ua7oiylpmiow8iy1smace199(String _$Ua7oiylpmiow8iy1smace199) {
            this._$Ua7oiylpmiow8iy1smace199 = _$Ua7oiylpmiow8iy1smace199;
        }

        public String get_$Ua7oiylpmiow8iy1smacm301() {
            return _$Ua7oiylpmiow8iy1smacm301;
        }

        public void set_$Ua7oiylpmiow8iy1smacm301(String _$Ua7oiylpmiow8iy1smacm301) {
            this._$Ua7oiylpmiow8iy1smacm301 = _$Ua7oiylpmiow8iy1smacm301;
        }

        public String get_$Ua7oiylpmiow8iy1smac0139() {
            return _$Ua7oiylpmiow8iy1smac0139;
        }

        public void set_$Ua7oiylpmiow8iy1smac0139(String _$Ua7oiylpmiow8iy1smac0139) {
            this._$Ua7oiylpmiow8iy1smac0139 = _$Ua7oiylpmiow8iy1smac0139;
        }

        public String get_$Ua7oiylpmiow8iy1smacd78() {
            return _$Ua7oiylpmiow8iy1smacd78;
        }

        public void set_$Ua7oiylpmiow8iy1smacd78(String _$Ua7oiylpmiow8iy1smacd78) {
            this._$Ua7oiylpmiow8iy1smacd78 = _$Ua7oiylpmiow8iy1smacd78;
        }

        public String get_$Ua7oiylpmiow8iy1smacc238() {
            return _$Ua7oiylpmiow8iy1smacc238;
        }

        public void set_$Ua7oiylpmiow8iy1smacc238(String _$Ua7oiylpmiow8iy1smacc238) {
            this._$Ua7oiylpmiow8iy1smacc238 = _$Ua7oiylpmiow8iy1smacc238;
        }

        public String get_$Ua7oiylpmiow8iy1smacf25() {
            return _$Ua7oiylpmiow8iy1smacf25;
        }

        public void set_$Ua7oiylpmiow8iy1smacf25(String _$Ua7oiylpmiow8iy1smacf25) {
            this._$Ua7oiylpmiow8iy1smacf25 = _$Ua7oiylpmiow8iy1smacf25;
        }

        public String get_$Ua7oiylpmiow8iy1smac4306() {
            return _$Ua7oiylpmiow8iy1smac4306;
        }

        public void set_$Ua7oiylpmiow8iy1smac4306(String _$Ua7oiylpmiow8iy1smac4306) {
            this._$Ua7oiylpmiow8iy1smac4306 = _$Ua7oiylpmiow8iy1smac4306;
        }

        public String get_$Ua07oiylpmiow8iy1smadu167() {
            return _$Ua07oiylpmiow8iy1smadu167;
        }

        public void set_$Ua07oiylpmiow8iy1smadu167(String _$Ua07oiylpmiow8iy1smadu167) {
            this._$Ua07oiylpmiow8iy1smadu167 = _$Ua07oiylpmiow8iy1smadu167;
        }

        public String get_$Ua7oiylpmiow8iy1smacg270() {
            return _$Ua7oiylpmiow8iy1smacg270;
        }

        public void set_$Ua7oiylpmiow8iy1smacg270(String _$Ua7oiylpmiow8iy1smacg270) {
            this._$Ua7oiylpmiow8iy1smacg270 = _$Ua7oiylpmiow8iy1smacg270;
        }

        public String get_$Ua7oiylpmiow8iy1smac3305() {
            return _$Ua7oiylpmiow8iy1smac3305;
        }

        public void set_$Ua7oiylpmiow8iy1smac3305(String _$Ua7oiylpmiow8iy1smac3305) {
            this._$Ua7oiylpmiow8iy1smac3305 = _$Ua7oiylpmiow8iy1smac3305;
        }

        public String get_$Ua7oiylpmiow8iy1smacj232() {
            return _$Ua7oiylpmiow8iy1smacj232;
        }

        public void set_$Ua7oiylpmiow8iy1smacj232(String _$Ua7oiylpmiow8iy1smacj232) {
            this._$Ua7oiylpmiow8iy1smacj232 = _$Ua7oiylpmiow8iy1smacj232;
        }

        public String get_$Ua7oiylpmiow8iy1smach164() {
            return _$Ua7oiylpmiow8iy1smach164;
        }

        public void set_$Ua7oiylpmiow8iy1smach164(String _$Ua7oiylpmiow8iy1smach164) {
            this._$Ua7oiylpmiow8iy1smach164 = _$Ua7oiylpmiow8iy1smach164;
        }

        public String get_$Ua7oiylpmiow8iy1smac699() {
            return _$Ua7oiylpmiow8iy1smac699;
        }

        public void set_$Ua7oiylpmiow8iy1smac699(String _$Ua7oiylpmiow8iy1smac699) {
            this._$Ua7oiylpmiow8iy1smac699 = _$Ua7oiylpmiow8iy1smac699;
        }

        public String get_$Ua7oiylpmiow8iy1smacb254() {
            return _$Ua7oiylpmiow8iy1smacb254;
        }

        public void set_$Ua7oiylpmiow8iy1smacb254(String _$Ua7oiylpmiow8iy1smacb254) {
            this._$Ua7oiylpmiow8iy1smacb254 = _$Ua7oiylpmiow8iy1smacb254;
        }

        public String get_$Ua7oiylpmiow8iy1smaca306() {
            return _$Ua7oiylpmiow8iy1smaca306;
        }

        public void set_$Ua7oiylpmiow8iy1smaca306(String _$Ua7oiylpmiow8iy1smaca306) {
            this._$Ua7oiylpmiow8iy1smaca306 = _$Ua7oiylpmiow8iy1smaca306;
        }

        public String get_$Ua7oiylpmiow8iy1smac5167() {
            return _$Ua7oiylpmiow8iy1smac5167;
        }

        public void set_$Ua7oiylpmiow8iy1smac5167(String _$Ua7oiylpmiow8iy1smac5167) {
            this._$Ua7oiylpmiow8iy1smac5167 = _$Ua7oiylpmiow8iy1smac5167;
        }
    }

    public static class CitiesBean {

        @SerializedName("7oiylpmiow8iy1smadm")
        private String _$7oiylpmiow8iy1smadm;
        @SerializedName("7oiylpmiow8iy1smadn")
        private String _$7oiylpmiow8iy1smadn;
        @SerializedName("7oiylpmiow8iy1smadi")
        private String _$7oiylpmiow8iy1smadi;
        @SerializedName("7oiylpmiow8iy1smaeb")
        private String _$7oiylpmiow8iy1smaeb;
        @SerializedName("7oiylpmiow8iy1smadr")
        private String _$7oiylpmiow8iy1smadr;
        @SerializedName("7oiylpmiow8iy1smadk")
        private String _$7oiylpmiow8iy1smadk;
        @SerializedName("7oiylpmiow8iy1smadx")
        private String _$7oiylpmiow8iy1smadx;
        @SerializedName("7oiylpmiow8iy1smadp")
        private String _$7oiylpmiow8iy1smadp;
        @SerializedName("7oiylpmiow8iy1smadj")
        private String _$7oiylpmiow8iy1smadj;
        @SerializedName("7oiylpmiow8iy1smaec")
        private String _$7oiylpmiow8iy1smaec;

        public String get_$7oiylpmiow8iy1smadm() {
            return _$7oiylpmiow8iy1smadm;
        }

        public void set_$7oiylpmiow8iy1smadm(String _$7oiylpmiow8iy1smadm) {
            this._$7oiylpmiow8iy1smadm = _$7oiylpmiow8iy1smadm;
        }

        public String get_$7oiylpmiow8iy1smadn() {
            return _$7oiylpmiow8iy1smadn;
        }

        public void set_$7oiylpmiow8iy1smadn(String _$7oiylpmiow8iy1smadn) {
            this._$7oiylpmiow8iy1smadn = _$7oiylpmiow8iy1smadn;
        }

        public String get_$7oiylpmiow8iy1smadi() {
            return _$7oiylpmiow8iy1smadi;
        }

        public void set_$7oiylpmiow8iy1smadi(String _$7oiylpmiow8iy1smadi) {
            this._$7oiylpmiow8iy1smadi = _$7oiylpmiow8iy1smadi;
        }

        public String get_$7oiylpmiow8iy1smaeb() {
            return _$7oiylpmiow8iy1smaeb;
        }

        public void set_$7oiylpmiow8iy1smaeb(String _$7oiylpmiow8iy1smaeb) {
            this._$7oiylpmiow8iy1smaeb = _$7oiylpmiow8iy1smaeb;
        }

        public String get_$7oiylpmiow8iy1smadr() {
            return _$7oiylpmiow8iy1smadr;
        }

        public void set_$7oiylpmiow8iy1smadr(String _$7oiylpmiow8iy1smadr) {
            this._$7oiylpmiow8iy1smadr = _$7oiylpmiow8iy1smadr;
        }

        public String get_$7oiylpmiow8iy1smadk() {
            return _$7oiylpmiow8iy1smadk;
        }

        public void set_$7oiylpmiow8iy1smadk(String _$7oiylpmiow8iy1smadk) {
            this._$7oiylpmiow8iy1smadk = _$7oiylpmiow8iy1smadk;
        }

        public String get_$7oiylpmiow8iy1smadx() {
            return _$7oiylpmiow8iy1smadx;
        }

        public void set_$7oiylpmiow8iy1smadx(String _$7oiylpmiow8iy1smadx) {
            this._$7oiylpmiow8iy1smadx = _$7oiylpmiow8iy1smadx;
        }

        public String get_$7oiylpmiow8iy1smadp() {
            return _$7oiylpmiow8iy1smadp;
        }

        public void set_$7oiylpmiow8iy1smadp(String _$7oiylpmiow8iy1smadp) {
            this._$7oiylpmiow8iy1smadp = _$7oiylpmiow8iy1smadp;
        }

        public String get_$7oiylpmiow8iy1smadj() {
            return _$7oiylpmiow8iy1smadj;
        }

        public void set_$7oiylpmiow8iy1smadj(String _$7oiylpmiow8iy1smadj) {
            this._$7oiylpmiow8iy1smadj = _$7oiylpmiow8iy1smadj;
        }

        public String get_$7oiylpmiow8iy1smaec() {
            return _$7oiylpmiow8iy1smaec;
        }

        public void set_$7oiylpmiow8iy1smaec(String _$7oiylpmiow8iy1smaec) {
            this._$7oiylpmiow8iy1smaec = _$7oiylpmiow8iy1smaec;
        }
    }

    public static class OrganizationsBean {
        /**
         * id : 7oiylpmiow8iy1smaze
         * oldId : 1233
         * orgType : 1
         * branch : false
         * title : А-Банк
         * regionId : ua,7oiylpmiow8iy1smaci
         * cityId : 7oiylpmiow8iy1smadm
         * phone : 0800500809
         * address : ул. Батумская, 11
         * link : http://organizations.finance.ua/ru/info/currency/-/7oiylpmiow8iy1smaze/cash
         * currencies : {"EUR":{"ask":"29.3000","bid":"28.3000"},"RUB":{"ask":"0.4810","bid":"0.4550"},"USD":{"ask":"27.8000","bid":"26.8000"}}
         */

        private String id;
        private int oldId;
        private int orgType;
        private boolean branch;
        private String title;
        private String regionId;
        private String cityId;
        private String phone;
        private String address;
        private String link;
        private CurrenciesBeanX currencies;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getOldId() {
            return oldId;
        }

        public void setOldId(int oldId) {
            this.oldId = oldId;
        }

        public int getOrgType() {
            return orgType;
        }

        public void setOrgType(int orgType) {
            this.orgType = orgType;
        }

        public boolean isBranch() {
            return branch;
        }

        public void setBranch(boolean branch) {
            this.branch = branch;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRegionId() {
            return regionId;
        }

        public void setRegionId(String regionId) {
            this.regionId = regionId;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public CurrenciesBeanX getCurrencies() {
            return currencies;
        }

        public void setCurrencies(CurrenciesBeanX currencies) {
            this.currencies = currencies;
        }

        public static class CurrenciesBeanX {
            /**
             * EUR : {"ask":"29.3000","bid":"28.3000"}
             * RUB : {"ask":"0.4810","bid":"0.4550"}
             * USD : {"ask":"27.8000","bid":"26.8000"}
             */

            private EURBean EUR;
            private RUBBean RUB;
            private USDBean USD;

            public EURBean getEUR() {
                return EUR;
            }

            public void setEUR(EURBean EUR) {
                this.EUR = EUR;
            }

            public RUBBean getRUB() {
                return RUB;
            }

            public void setRUB(RUBBean RUB) {
                this.RUB = RUB;
            }

            public USDBean getUSD() {
                return USD;
            }

            public void setUSD(USDBean USD) {
                this.USD = USD;
            }

            public static class EURBean {
                /**
                 * ask : 29.3000
                 * bid : 28.3000
                 */

                private String ask;
                private String bid;

                public String getAsk() {
                    return ask;
                }

                public void setAsk(String ask) {
                    this.ask = ask;
                }

                public String getBid() {
                    return bid;
                }

                public void setBid(String bid) {
                    this.bid = bid;
                }
            }

            public static class RUBBean {
                /**
                 * ask : 0.4810
                 * bid : 0.4550
                 */

                private String ask;
                private String bid;

                public String getAsk() {
                    return ask;
                }

                public void setAsk(String ask) {
                    this.ask = ask;
                }

                public String getBid() {
                    return bid;
                }

                public void setBid(String bid) {
                    this.bid = bid;
                }
            }

            public static class USDBean {
                /**
                 * ask : 27.8000
                 * bid : 26.8000
                 */

                private String ask;
                private String bid;

                public String getAsk() {
                    return ask;
                }

                public void setAsk(String ask) {
                    this.ask = ask;
                }

                public String getBid() {
                    return bid;
                }

                public void setBid(String bid) {
                    this.bid = bid;
                }
            }
        }
    }
}