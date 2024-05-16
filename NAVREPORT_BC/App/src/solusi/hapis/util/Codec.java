package solusi.hapis.util;

import org.apache.commons.lang.StringUtils;


/**
 * @author <a href="dbbottle@gmail.com">hermanto</a>
 * @Date 12 Mar 12
 * ==================================================================
 * Copyright (c) 2012  All rights reserved.
 * ==================================================================
 */

public class Codec {
	
	
    public enum Cabang implements CodecInterface{
    	JAKARTA("10", "10-JAKARTA"),
    	SURABAYA("15", "15-SURABAYA"),
    	CIKARANG("16", "16-CIKARANG"),
    	SEMARANG("17", "17-SEMARANG"),
    	BALI("19", "19-BALI");

        private final String value;
        private final String label;

        private Cabang(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(String kode){
    		for (Cabang cabang : Cabang.values()) {
				if(cabang.getValue().equals(kode)) {
					return cabang.getLabel();
				}
			}
    		return null;
    	}
    }

    

    public enum RightType implements CodecInterface {
    	CATEGORY("1", "CATEGORY"),
    	MENUITEM("2", "MENU ITEM"),
    	BUTTON("6", "BUTTON");

        private final String value;
        private final String label;

        private RightType(String _value, String _label) {
            this.value = _value;
            this.label = _label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(int code){
    		if (code > 0) {
    			String vValue = Integer.toString(code);
	    		for (RightType tipe : RightType.values()) {
	    			if (vValue.equals(tipe.getValue())) {
	    				return tipe.getLabel();
	    			}
	    		}
    		}
    		return null;
    	}
    }
    
    
    public enum StatusDokumen implements CodecInterface {
    	APPROVE("P", "Approve"),
    	AKTIF("A", "Aktif"),
    	OPEN("O", "Open"),
    	CANCEL("C", "Cancel"),
    	POSTED("F", "Posted"),
    	CLOSE("E", "Close"),
    	TERMINATE("T", "Terminate"),
    	FINAL("S", "Final"),
    	PENDING("Q", "Pending"),
    	REQUEST("R", "Request"),
    	TOLAK("L", "Tolak"),
    	KIRIM("K", "Kirim"),
    	TRANSIT("N", "Transit"),
    	TITIPAN("I", "Titip"),
    	KLIRING("G", "Kliring"),
    	SETOR("B", "Setor");

        private final String value;
        private final String label;

        private StatusDokumen(String _value, String _label) {
            this.value = _value;
            this.label = _label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(String code){
    		if(StringUtils.isNotEmpty(code))
    		for (StatusDokumen statusDok : StatusDokumen.values()) {
    			if(code.equals(statusDok.getValue()))
    				return statusDok.getLabel();
    		}
    		
    		return null;
    	}
    }
    
    public enum TipeSecurity implements CodecInterface {
    	CATEGORI("1", "Menu Category"),
    	MENUITEM("2", "Menu Item"),
    	COMPONENT("6", "Component");

        private final String value;
        private final String label;

        private TipeSecurity(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(int kode){
    		if(kode != 0) {
    			String code = Integer.toString(kode);
	    		for (TipeSecurity jns : TipeSecurity.values()) {
	    			if(code.equals(jns.getValue()))
	    				return jns.getLabel();
	    		}
    		}
    		return null;
    	}
    }
    
    public enum Answer {
    	TIDAK(0, "Tidak"),
    	YA(1, "Ya");

        private final int value;
        private final String label;

        private Answer(int value, String label) {
            this.value = value;
            this.label = label;
        }

        public int getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(int kode){
    		for (Answer answer : Answer.values()) {
				if(answer.getValue() == kode)
					return answer.getLabel();
			}
    		return null;
    	}
    }
    
    public enum Answer2 implements CodecInterface{
    	TIDAK("T", "Tidak"),
    	YA("Y", "Ya");

        private final String value;
        private final String label;

        private Answer2(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(String kode){
    		for (Answer2 answer2 : Answer2.values()) {
				if(answer2.getValue().equals(kode)) {
					return answer2.getLabel();
				}
			}
    		return null;
    	}
    }
    
    public enum Answer3 implements CodecInterface{
    	TIDAK("0", "Tidak"),
    	YA("1", "Ya");

        private final String value;
        private final String label;

        private Answer3(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(String kode){
    		for (Answer3 answer3 : Answer3.values()) {
				if(answer3.getValue().equals(kode)) {
					return answer3.getLabel();
				}
			}
    		return null;
    	}
    }
    
    public enum Answer4 implements CodecInterface{
    	TIDAK(" ", "Tidak"),
    	YA("1", "Ya");

        private final String value;
        private final String label;

        private Answer4(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(String kode){
    		for (Answer4 answer4 : Answer4.values()) {
				if(answer4.getValue().equals(kode)) {
					return answer4.getLabel();
				}
			}
    		return null;
    	}
    }
    public enum JnsPensiun implements CodecInterface{
    	PENSIUN("1", "Pensiun"),
    	NONPENSIUN("2", "Non Pensiun");

        private final String value;
        private final String label;

        private JnsPensiun(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(String kode){
    		for (JnsPensiun jnsPensiun : JnsPensiun.values()) {
				if(jnsPensiun.getValue().equals(kode)) {
					return jnsPensiun.getLabel();
				}
			}
    		return null;
    	}
    }
    
    
    public enum Form1721 implements CodecInterface{
    	A1("A1", "A1"),
    	B("B", "B");

        private final String value;
        private final String label;

        private Form1721(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(String kode){
    		for (Form1721 form1721 : Form1721.values()) {
				if(form1721.getValue().equals(kode)) {
					return form1721.getLabel();
				}
			}
    		return null;
    	}
    }
    
    
    public enum PendPot implements CodecInterface{
    	PENDAPATAN("D", "Pendapatan"),
    	POTONGAN("P", "Potongan");

        private final String value;
        private final String label;

        private PendPot(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(String kode){
    		for (PendPot pendpot : PendPot.values()) {
				if(pendpot.getValue().equals(kode)) {
					return pendpot.getLabel();
				}
			}
    		return null;
    	}
    }
    
    public enum JnsGaji implements CodecInterface{
    	BULANAN("B", "Bulanan"),
    	HARIAN("H", "Harian");

        private final String value;
        private final String label;

        private JnsGaji(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(String kode){
    		for (JnsGaji jnsgaji : JnsGaji.values()) {
				if(jnsgaji.getValue().equals(kode)) {
					return jnsgaji.getLabel();
				}
			}
    		return null;
    	}
    }

    
    public enum MaritalStatus implements CodecInterface{
    	LAJANG("L", "Lajang"),
    	KAWIN("K", "Kawin"),
    	JANDA("X", "Janda"),
    	DUDA("D", "Duda");

        private final String value;
        private final String label;

        private MaritalStatus(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(String kode){
    		for (MaritalStatus maritalSts : MaritalStatus.values()) {
				if(maritalSts.getValue().equals(kode)) {
					return maritalSts.getLabel();
				}
			}
    		return null;
    	}
    }
    
    public enum StatusPajak implements CodecInterface{
    	L0("L0", "L0"),
    	L1("L1", "L1"),
    	L2("L2", "L2"),
    	L3("L3", "L3"),
    	K0("K0", "K0"),
    	K1("K1", "K1"),
    	K2("K2", "K2"),
    	K3("K3", "K3");

        private final String value;
        private final String label;

        private StatusPajak(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(String kode){
    		for (StatusPajak statusPjk : StatusPajak.values()) {
				if(statusPjk.getValue().equals(kode)) {
					return statusPjk.getLabel();
				}
			}
    		return null;
    	}
    }
    
    public enum Agama implements CodecInterface{
    	ISLAM("1", "Islam"),
    	KRISTEN("2", "Kristen"),
    	KATOLIK("3", "Katolik"),
    	BUDHA("4", "Budha"),
    	HINDU("5", "Hindu"),
    	LAINNYA("6", "Lainnya");

        private final String value;
        private final String label;

        private Agama(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(String kode){
    		for (Agama agama : Agama.values()) {
				if(agama.getValue().equals(kode)) {
					return agama.getLabel();
				}
			}
    		return null;
    	}
    }

    
    public enum Pendidikan implements CodecInterface{
    	SD("1", "SD"),
    	SMP("2", "SMP"),
    	SMA("3", "SMA"),
    	AKADEMI("4", "AKADEMI"),
    	S1("5", "S1"),
    	S2("6", "S2"),
    	S3("7", "S3");

        private final String value;
        private final String label;

        private Pendidikan(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(String kode){
    		for (Pendidikan pendidikan : Pendidikan.values()) {
				if(pendidikan.getValue().equals(kode)) {
					return pendidikan.getLabel();
				}
			}
    		return null;
    	}
    }

    
    public enum JnsKelamin implements CodecInterface{
    	LAKI("L", "Laki-laki"),
    	PEREMPUAN("P", "Perempuan");

        private final String value;
        private final String label;

        private JnsKelamin(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(String kode){
    		for (JnsKelamin jnsKelamin : JnsKelamin.values()) {
				if(jnsKelamin.getValue().equals(kode)) {
					return jnsKelamin.getLabel();
				}
			}
    		return null;
    	}
    }

    
    public enum Comp implements CodecInterface{
    	LANGSUNG("L", "Langsung"),
    	PAYROLL("P", "Payroll");

        private final String value;
        private final String label;

        private Comp(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(String kode){
    		for (Comp comp : Comp.values()) {
				if(comp.getValue().equals(kode)) {
					return comp.getLabel();
				}
			}
    		return null;
    	}
    }

    
    public enum StatusInOut implements CodecInterface{
    	MASUK("M", "Masuk"),
    	KELUAR("K", "Keluar");

        private final String value;
        private final String label;

        private StatusInOut(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(String kode){
    		for (StatusInOut sts : StatusInOut.values()) {
				if(sts.getValue().equals(kode)) {
					return sts.getLabel();
				}
			}
    		return null;
    	}
    }


    
    public enum HariLemKerja implements CodecInterface{
    	LEMBUR("L", "Lembur"),
    	KERJA("K", "Kerja"),
    	ISTIMEWA("I", "Istimewa");

        private final String value;
        private final String label;

        private HariLemKerja(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(String kode){
    		for (HariLemKerja hari : HariLemKerja.values()) {
				if(hari.getValue().equals(kode)) {
					return hari.getLabel();
				}
			}
    		return null;
    	}
    }
    
    public enum TipeLap implements CodecInterface{
    	SLIPGAJI("1", "Slip Gaji"),
    	ARSIPGAJI("2", "Arsip Gaji"),
    	KARTUSLIP("3", "Kartu Slip");

        private final String value;
        private final String label;

        private TipeLap(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(String kode){
    		for (TipeLap tipe : TipeLap.values()) {
				if(tipe.getValue().equals(kode)) {
					return tipe.getLabel();
				}
			}
    		return null;
    	}
    }

    
    public enum Program implements CodecInterface {
    	JKK("JKK", "JKK"),
    	JHT("JHT", "JHT"),
    	JKM("JKM", "JKM"),
    	JPK("JPK", "JPK");

        private final String value;
        private final String label;

        private Program(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(String kode){
    		for (Program prg : Program.values()) {
				if(prg.getValue().equals(kode)) {
					return prg.getLabel();
				}
			}
    		return null;
    	}
    }

    
    public enum LevelJKK {
    	NOL(0, "0"),
    	SATU(1, "1"),
    	DUA(2, "2"),
    	TIGA(3, "3"),
    	EMPAT(4, "4"),
    	LIMA(5, "5");

        private final Integer value;
        private final String label;

        private LevelJKK(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(Integer kode){
    		for (LevelJKK level : LevelJKK.values()) {
				if(level.getValue().equals(kode)) {
					return level.getLabel();
				}
			}
    		return null;
    	}
    }

    
    public enum Hubungan implements CodecInterface {
    	PASANGAN("1", "Pasangan"),
    	ANAK("2", "Anak"),
    	AYAH("3", "Ayah"),
    	IBU("4", "Ibu"),
    	LAINLAIN("5", "Lain-lain");

        private final String value;
        private final String label;

        private Hubungan(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(String kode){
    		for (Hubungan hub : Hubungan.values()) {
				if(hub.getValue().equals(kode)) {
					return hub.getLabel();
				}
			}
    		return null;
    	}
    }

    
    public enum SistemPajakParam implements CodecInterface {
    	GROSS("G", "Gross"),
    	NETTO("N", "Netto"),
    	NETTOGROSUP("R", "");

        private final String value;
        private final String label;

        private SistemPajakParam(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(String kode){
    		for (SistemPajakParam pjk : SistemPajakParam.values()) {
				if(pjk.getValue().equals(kode)) {
					return pjk.getLabel();
				}
			}
    		return null;
    	}
    }

    
    public enum SistemPajak implements CodecInterface {
    	DEFAULT("D", "Default Pegawai"),
    	GROSS("G", "Gross"),
    	NETTO("N", "Netto"),
    	NETTOGROSUP("R", "Netto (Gross Up)");

        private final String value;
        private final String label;

        private SistemPajak(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(String kode){
    		for (SistemPajak pjk : SistemPajak.values()) {
				if(pjk.getValue().equals(kode)) {
					return pjk.getLabel();
				}
			}
    		return null;
    	}
    }
    
    public enum Anakke implements CodecInterface {
    	NA("N", "N/A"),
    	SATU("1", "1"),
    	DUA("2", "2"),
    	TIGA("3", "3");

        private final String value;
        private final String label;

        private Anakke(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name();
        }
        
    	public static String getLabelByCode(String kode){
    		for (Anakke anak : Anakke.values()) {
				if(anak.getValue().equals(kode)) {
					return anak.getLabel();
				}
			}
    		return null;
    	}
    }
        public enum Pembulatan implements CodecInterface {
        	SATU("1", "1(Satuan)"),
        	DUA("2", "10(Puluhan)"),
        	TIGA("3", "100(Ratusan)");

            private final String value;
            private final String label;

            private Pembulatan(String value, String label) {
                this.value = value;
                this.label = label;
            }

            public String getValue() {
                return this.value;
            }

            public String getLabel() {
                return this.label;
            }

            public String getName() {
                return this.name();
            }
            
        	public static String getLabelByCode(String kode){
        		for (Pembulatan pembulatan : Pembulatan.values()) {
    				if(pembulatan.getValue().equals(kode)) {
    					return pembulatan.getLabel();
    				}
    			}
        		return null;
        	}
        	
        }
        
        public enum FlagAngsuran implements CodecInterface {
        	NON(" ", "Non"),
        	POKOK("A", "Pokok"),
        	BUNGA("B", "Bunga");

            private final String value;
            private final String label;

            private FlagAngsuran(String value, String label) {
                this.value = value;
                this.label = label;
            }

            public String getValue() {
                return this.value;
            }

            public String getLabel() {
                return this.label;
            }

            public String getName() {
                return this.name();
            }
            
        	public static String getLabelByCode(String kode){
        		for (FlagAngsuran flag : FlagAngsuran.values()) {
    				if(flag.getValue().equals(kode)) {
    					return flag.getLabel();
    				}
    			}
        		return null;
        	}
        	
        }
    
        public enum SistemLevel implements CodecInterface {
        	SATU("1", "1"),
        	DUA("2", "2"),
        	TIGA("3", "3"),
        	EMPAT("4", "4"),
        	LIMA("5", "5");

            private final String value;
            private final String label;

            private SistemLevel(String value, String label) {
                this.value = value;
                this.label = label;
            }

            public String getValue() {
                return this.value;
            }

            public String getLabel() {
                return this.label;
            }

            public String getName() {
                return this.name();
            }
            
        	public static String getLabelByCode(String kode){
        		for (SistemLevel lvl : SistemLevel.values()) {
    				if(lvl.getValue().equals(kode)) {
    					return lvl.getLabel();
    				}
    			}
        		return null;
        	}
        }
    
        public enum SistemLevel7 implements CodecInterface {
        	SATU("1", "Area"),
        	DUA("2", "Cabang"),
        	TIGA("3", "Unit Usaha"),
        	EMPAT("4", "Unit Kerja"),
        	LIMA("5", "Golongan"),
        	ENAM("6", "Kelompok Jabatan"),
        	TUJUH("7", "Jabatan");

            private final String value;
            private final String label;

            private SistemLevel7(String value, String label) {
                this.value = value;
                this.label = label;
            }

            public String getValue() {
                return this.value;
            }

            public String getLabel() {
                return this.label;
            }

            public String getName() {
                return this.name();
            }
            
        	public static String getLabelByCode(String kode){
        		for (SistemLevel7 lvl : SistemLevel7.values()) {
    				if(lvl.getValue().equals(kode)) {
    					return lvl.getLabel();
    				}
    			}
        		return null;
        	}
        }
    
        public enum SistemLevel8 implements CodecInterface {
        	SATU("1", "Area"),
        	DUA("2", "Cabang"),
        	TIGA("3", "Unit Usaha"),
        	EMPAT("4", "Unit Kerja"),
        	LIMA("5", "Golongan"),
        	ENAM("6", "Kelompok Jabatan"),
        	TUJUH("7", "Jabatan"),
        	DELAPAN("8", "None");

            private final String value;
            private final String label;

            private SistemLevel8(String value, String label) {
                this.value = value;
                this.label = label;
            }

            public String getValue() {
                return this.value;
            }

            public String getLabel() {
                return this.label;
            }

            public String getName() {
                return this.name();
            }
            
        	public static String getLabelByCode(String kode){
        		for (SistemLevel8 lvl8 : SistemLevel8.values()) {
    				if(lvl8.getValue().equals(kode)) {
    					return lvl8.getLabel();
    				}
    			}
        		return null;
        	}
        }
        
        public enum FlagPayroll implements CodecInterface {
        	HARI(" ", "Bulanan/Harian"),
        	KHUSUS("@", "Khusus"),
        	THR("*", "THR"),
        	MANTAN("#", "Mantan Pegawai");

            private final String value;
            private final String label;

            private FlagPayroll(String value, String label) {
                this.value = value;
                this.label = label;
            }

            public String getValue() {
                return this.value;
            }

            public String getLabel() {
                return this.label;
            }

            public String getName() {
                return this.name();
            }
            
        	public static String getLabelByCode(String kode){
        		for (FlagPayroll flg : FlagPayroll.values()) {
    				if(flg.getValue().equals(kode)) {
    					return flg.getLabel();
    				}
    			}
        		return null;
        	}
        }
        
        public enum Penilaian implements CodecInterface {
        	TP("TP", "Tidak Ada Penilaian "),
        	BS("BS", "Baik Sekali"),
        	BK("BK", "Baik"),
        	CP("CP", "Cukup"),
        	KG("KG", "Kurang"),
        	KS("KS", "Kurang Sekali");
        	

            private final String value;
            private final String label;

            private Penilaian(String value, String label) {
                this.value = value;
                this.label = label;
            }

            public String getValue() {
                return this.value;
            }

            public String getLabel() {
                return this.label;
            }

            public String getName() {
                return this.name();
            }
            
        	public static String getLabelByCode(String kode){
        		for (Penilaian nilai : Penilaian.values()) {
    				if(nilai.getValue().equals(kode)) {
    					return nilai.getLabel();
    				}
    			}
        		return null;
        	}
        }
        
        public enum StatusKemampuan implements CodecInterface {
        	AKTIF("1", "Aktif"),
        	PASIF("2", "Pasif");

            private final String value;
            private final String label;

            private StatusKemampuan(String value, String label) {
                this.value = value;
                this.label = label;
            }

            public String getValue() {
                return this.value;
            }

            public String getLabel() {
                return this.label;
            }

            public String getName() {
                return this.name();
            }
            
        	public static String getLabelByCode(String code){
        		for(StatusKemampuan statusKemampuan : StatusKemampuan.values()) {
        		  if(statusKemampuan.getValue().equals(code)) {
        		   return statusKemampuan.getLabel();
        		  }
        		}
        		
        		return null;
        	}
        }
        
        public enum Sifat implements CodecInterface {
        	BULANAN("B", "Bulanan"),
        	TAHUNAN("T", "Tahunan");

            private final String value;
            private final String label;

            private Sifat(String value, String label) {
                this.value = value;
                this.label = label;
            }

            public String getValue() {
                return this.value;
            }

            public String getLabel() {
                return this.label;
            }

            public String getName() {
                return this.name();
            }
            
        	public static String getLabelByCode(String kode){
        		for (Sifat sft : Sifat.values()) {
    				if(sft.getValue().equals(kode)) {
    					return sft.getLabel();
    				}
    			}
        		return null;
        	}
        }
        
        public enum Sumber implements CodecInterface {
        	ENTRY("L", "D/Entry"),
        	PROSES("P", "Proses");

            private final String value;
            private final String label;

            private Sumber(String value, String label) {
                this.value = value;
                this.label = label;
            }

            public String getValue() {
                return this.value;
            }

            public String getLabel() {
                return this.label;
            }

            public String getName() {
                return this.name();
            }
            
        	public static String getLabelByCode(String kode){
        		for (Sumber smbr : Sumber.values()) {
    				if(smbr.getValue().equals(kode)) {
    					return smbr.getLabel();
    				}
    			}
        		return null;
        	}
        }
        
        public enum Kelompok implements CodecInterface {
        	ALL("", "All"),
        	SATU("1", "1"),
        	DUA("2", "2"),
        	TIGA("3", "3"),
        	EMPAT("4", "4"),
        	LIMA("5", "5");
            
            private final String value;
            private final String label;

            private Kelompok(String value, String label) {
                this.value = value;
                this.label = label;
            }

            public String getValue() {
                return this.value;
            }

            public String getLabel() {
                return this.label;
            }

            public String getName() {
                return this.name();
            }
            
        	public static String getLabelByCode(String kode){
        		for (Kelompok kelompok : Kelompok.values()) {
    				if(kelompok.getValue().equals(kode))
    					return kelompok.getLabel();
    			}
        		return null;
        	}
        }

        
        public enum SlipUrut implements CodecInterface {
        	NONE("0", "N/A"),
        	CABANG("1", "CABANG/PT"),
        	UUSAHA("2", "UNIT USAHA"),
        	UKER("3", "UNIT KERJA"),
        	AREA("4", "AREA"),
        	JABATAN("5", "JABATAN"),
        	GOLONGAN("6", "GOLONGAN"),
        	KJABATAN("7", "KELOMPOK JABATAN"),
        	KLASIFIKASI("8", "KLASIFIKASI"),
        	STSPJK("9", "STATUS PAJAK"),
        	NPWP("10", "NPWP");

            private final String value;
            private final String label;

            private SlipUrut(String value, String label) {
                this.value = value;
                this.label = label;
            }

            public String getValue() {
                return this.value;
            }

            public String getLabel() {
                return this.label;
            }

            public String getName() {
                return this.name();
            }
            
        	public static String getLabelByCode(String kode){
        		for (SlipUrut slipurut : SlipUrut.values()) {
    				if(slipurut.getValue().equals(kode))
    					return slipurut.getLabel();
    			}
        		return null;
        	}
        }
}