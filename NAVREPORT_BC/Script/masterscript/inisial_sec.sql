/* --------------------------------
INSERT SECURITY APLIKASI HAPIS-WEB
-----------------------------------*/

-----------------------------
-- Delete Data sec_right
-----------------------------
/*
DELETE FROM sec_right
select * from sec_right
*/
-----------------------------
------ Cross Module ---------
-----------------------------
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1, 1, 'cat_CrossModule', 0, 'Cross Module', 0);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (101, 2, 'subCat_StrukturOrg', 0, 'Struktur Organisasi', 1);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10101, 3, 'menuItem_M01area', 0, 'Area', 101);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010101, 4, 'button_M01area_btnNew', 0, 'New', 10101);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010102, 4, 'button_M01area_btnEdit', 0, 'Edit', 10101);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010103, 4, 'button_M01area_btnDelete', 0, 'Delete', 10101);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010104, 4, 'button_M01area_btnListing', 0, 'Listing', 10101);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10102, 3, 'menuItem_M08hcab', 0, 'Cabang', 101);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010201, 4, 'button_M08hcab_btnNew', 0, 'New', 10102);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010202, 4, 'button_M08hcab_btnEdit', 0, 'Edit', 10102);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010203, 4, 'button_M08hcab_btnDelete', 0, 'Delete', 10102);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010204, 4, 'button_M08hcab_btnListing', 0, 'Listing', 10102);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10103, 3, 'menuItem_M02uusa', 0, 'Unit Usaha', 101);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010301, 4, 'button_M02uusa_btnNew', 0, 'New', 10103);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010302, 4, 'button_M02uusa_btnEdit', 0, 'Edit', 10103);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010303, 4, 'button_M02uusa_btnDelete', 0, 'Delete', 10103);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010304, 4, 'button_M02uusa_btnListing', 0, 'Listing', 10103);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10104, 3, 'menuItem_M12hgol', 0, 'Golongan', 101);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010401, 4, 'button_M12hgol_btnNew', 0, 'New', 10104);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010402, 4, 'button_M12hgol_btnEdit', 0, 'Edit', 10104);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010403, 4, 'button_M12hgol_btnDelete', 0, 'Delete', 10104);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010404, 4, 'button_M12hgol_btnListing', 0, 'Listing', 10104);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10105, 3, 'menuItem_M17uker', 0, 'Unit Kerja', 101);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010501, 4, 'button_M17uker_btnNew', 0, 'New', 10105);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010502, 4, 'button_M17uker_btnEdit', 0, 'Edit', 10105);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010503, 4, 'button_M17uker_btnDelete', 0, 'Delete', 10105);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010504, 4, 'button_M17uker_btnListing', 0, 'Listing', 10105);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10106, 3, 'menuItem_M06hkjb', 0, 'Kelompok Jabatan', 101);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010601, 4, 'button_M06hkjb_btnNew', 0, 'New', 10106);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010602, 4, 'button_M06hkjb_btnEdit', 0, 'Edit', 10106);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010603, 4, 'button_M06hkjb_btnDelete', 0, 'Delete', 10106);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010604, 4, 'button_M06hkjb_btnListing', 0, 'Listing', 10106);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10107, 3, 'menuItem_M04hjab', 0, 'Jabatan', 101);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010701, 4, 'button_M04hjab_btnNew', 0, 'New', 10107);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010702, 4, 'button_M04hjab_btnEdit', 0, 'Edit', 10107);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010703, 4, 'button_M04hjab_btnDelete', 0, 'Delete', 10107);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010704, 4, 'button_M04hjab_btnListing', 0, 'Listing', 10107);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10108, 3, 'menuItem_M33jabt', 0, 'Tanda Tangan', 101);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010801, 4, 'button_M33jabt_btnNew', 0, 'New', 10108);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010802, 4, 'button_M33jabt_btnEdit', 0, 'Edit', 10108);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010803, 4, 'button_M33jabt_btnDelete', 0, 'Delete', 10108);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1010804, 4, 'button_M33jabt_btnListing', 0, 'Listing', 10108);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (102, 2, 'subCat_Pegawai', 0, 'Pegawai', 1);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10201, 3, 'menuItem_M10klas', 0, 'Klasifikasi Pegawai', 102);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1020101, 4, 'button_M10klas_btnNew', 0, 'New', 10201);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1020102, 4, 'button_M10klas_btnEdit', 0, 'Edit', 10201);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1020103, 4, 'button_M10klas_btnDelete', 0, 'Delete', 10201);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1020104, 4, 'button_M10klas_btnListing', 0, 'Listing', 10201);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10202, 3, 'menuItem_M15pega', 0, 'Pegawai', 102);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1020201, 4, 'button_M15pega_btnNew', 0, 'New', 10202);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1020202, 4, 'button_M15pega_btnEdit', 0, 'Edit', 10202);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1020203, 4, 'button_M15pega_btnDelete', 0, 'Delete', 10202);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1020204, 4, 'button_M15pega_btnListing', 0, 'Listing', 10202);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10203, 3, 'menuItem_M15pegaView', 0, 'Inquiry Pegawai', 102);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1020301, 4, 'button_M15pegaView_btnEdit', 0, 'View', 10203);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10204, 3, 'menuItem_M15pegaUpdate', 0, 'Update Informasi Pegawai', 102);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1020401, 4, 'button_M15pegaUpdate_btnEdit', 0, 'Edit', 10204);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1020402, 4, 'button_M15pegaUpdate_btnEmergensi', 0, 'Emergensi', 10204);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1020403, 4, 'button_M15pegaUpdate_btnEfektifStatus', 0, 'Efektif Status', 10204);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1020404, 4, 'button_M15pegaUpdate_btnKeluarga', 0, 'Keluarga', 10204);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1020405, 4, 'button_M15pegaUpdate_btnKontrak', 0, 'Kontrak', 10204);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1020406, 4, 'button_M15pegaUpdate_btnKeluar', 0, 'Keluar', 10204);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1020407, 4, 'button_M15pegaUpdate_btnAbsensi', 0, 'Absensi', 10204);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10205, 3, 'menuItem_M16alkl', 0, 'Alasan Keluar', 102);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1020501, 4, 'button_M16alkl_btnNew', 0, 'New', 10205);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1020502, 4, 'button_M16alkl_btnEdit', 0, 'Edit', 10205);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1020503, 4, 'button_M16alkl_btnDelete', 0, 'Delete', 10205);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1020504, 4, 'button_M16alkl_btnListing', 0, 'Listing', 10205);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10206, 3, 'menuItem_ExImpDataPeg', 0, 'Import Data Pegawai', 102);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (103, 2, 'subCat_LapPegawai', 0, 'Laporan Pegawai', 1);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10301, 3, 'menuItem_LapPgwKeluar', 0, 'Laporan Pegawai Keluar', 103);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10302, 3, 'menuItem_LapPgwKontrak', 0, 'Akhir Masa Kontrak', 103);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10303, 3, 'menuItem_LapPgwStaffList', 0, 'Staff List', 103);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10304, 3, 'menuItem_LapPgwUltah', 0, 'Ulang Tahun Pegawai', 103);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10305, 3, 'menuItem_LapPgwUltahUker', 0, 'Ulang Tahun Pegawai per Unit Kerja', 103);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10306, 3, 'menuItem_LapPgwBiodata', 0, 'Biodata Pegawai', 103);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10307, 3, 'menuItem_LapPgwDaftar', 0, 'Daftar Pegawai Baru', 103);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10308, 3, 'menuItem_LapPgwMasaKerja', 0, 'Masa Kerja Pegawai', 103);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10309, 3, 'menuItem_LapPgwProbation', 0, 'Akhir Masa Probation', 103);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10310, 3, 'menuItem_LapPgwPensiun', 0, 'Persiapan Pensiun', 103);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10311, 3, 'menuItem_LapPgwValidasiKlr', 0, 'Validasi Pegawai Keluar', 103);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10312, 3, 'menuItem_LapPgwKeluarga', 0, 'Daftar Pegawai Beserta Keluarga', 103);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (104, 2, 'subCat_Absensi', 0, 'Absensi', 1);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (10401, 3, 'menuItem_M29jnsa', 0, 'Jenis Absen', 104);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1040101, 4, 'button_M29jnsa_btnNew', 0, 'New', 10401);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1040102, 4, 'button_M29jnsa_btnEdit', 0, 'Edit', 10401);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1040103, 4, 'button_M29jnsa_btnDelete', 0, 'Delete', 10401);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (1040104, 4, 'button_M29jnsa_btnListing', 0, 'Listing', 10401);

-----------------------------
--------- Payroll -----------
-----------------------------
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2, 1, 'cat_Payroll', 0, 'Payroll', 0);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (201, 2, 'subCat_Payroll_Parameter', 0, 'Parameter', 2);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20101, 3, 'menuItem_Fz1fldaPerusahaan', 0, 'Perusahaan', 201);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2010101, 4, 'button_Fz1fldaPerusahaan_btnEdit', 0, 'Edit', 20101);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20102, 3, 'menuItem_PindahCabang', 0, 'Pindah Cabang', 201);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2010201, 4, 'button_PindahCabang_btnEdit', 0, 'Edit', 20102);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2010202, 4, 'button_PindahCabang_btnListing', 0, 'Listing', 20102);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (202, 2, 'subCat_Payroll_Setup', 0, 'Setup', 2);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20201, 3, 'menuItem_M40curr', 0, 'Valuta', 202);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2020101, 4, 'button_M40curr_btnNew', 0, 'New', 20201);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2020102, 4, 'button_M40curr_btnEdit', 0, 'Edit', 20201);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2020103, 4, 'button_M40curr_btnDelete', 0, 'Delete', 20201);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2020104, 4, 'button_M40curr_btnListing', 0, 'Listing', 20201);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20202, 3, 'menuItem_M49kurs', 0, 'Kurs', 202);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2020201, 4, 'button_M49kurs_btnNew', 0, 'New', 20202);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2020202, 4, 'button_M49kurs_btnEdit', 0, 'Edit', 20202);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2020203, 4, 'button_M49kurs_btnDelete', 0, 'Delete', 20202);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2020204, 4, 'button_M49kurs_btnListing', 0, 'Listing', 20202);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20203, 3, 'menuItem_M03dppt', 0, 'Pendapatan/Potongan', 202);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2020301, 4, 'button_M03dppt_btnNew', 0, 'New', 20203);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2020302, 4, 'button_M03dppt_btnEdit', 0, 'Edit', 20203);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2020303, 4, 'button_M03dppt_btnDelete', 0, 'Delete', 20203);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2020304, 4, 'button_M03dppt_btnListing', 0, 'Listing', 20203);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20204, 3, 'menuItem_Fz1HariKerja', 0, 'Parameter Hari Kerja', 202);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2020401, 4, 'button_Fz1HariKerja_btnEdit', 0, 'Edit', 20204);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20205, 3, 'menuItem_T18hker', 0, 'Hari Kerja Pegawai Baru/Keluar', 202);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2020501, 4, 'button_T18hker_btnNew', 0, 'New', 20205);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2020502, 4, 'button_T18hker_btnEdit', 0, 'Edit', 20205);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2020503, 4, 'button_T18hker_btnDelete', 0, 'Delete', 20205);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2020504, 4, 'button_T18hker_btnListing', 0, 'Listing', 20205);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20206, 3, 'menuItem_M19hslg', 0, 'Format Slip', 202);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2020601, 4, 'button_M19hslg_btnNew', 0, 'New', 20206);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2020602, 4, 'button_M19hslg_btnEdit', 0, 'Edit', 20206);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2020603, 4, 'button_M19hslg_btnDelete', 0, 'Delete', 20206);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2020604, 4, 'button_M19hslg_btnListing', 0, 'Listing', 20206);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20207, 3, 'menuItem_CrossList', 0, 'Cross List', 202);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (203, 2, 'subCat_Payroll_KompGaji', 0, 'Komponen Gaji', 2);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20301, 3, 'menuItem_M11ggol', 0, 'Gaji Golongan', 203);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2030101, 4, 'button_M11ggol_btnNew', 0, 'New', 20301);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2030102, 4, 'button_M11ggol_btnEdit', 0, 'Edit', 20301);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2030103, 4, 'button_M11ggol_btnDelete', 0, 'Delete', 20301);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2030104, 4, 'button_M11ggol_btnListing', 0, 'Listing', 20301);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20302, 3, 'menuItem_M15pegaGaji', 0, 'Gaji Pegawai', 203);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2030201, 4, 'button_M15pegaGaji_btnPerusahaan', 0, 'Perusahaan', 20302);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2030202, 4, 'button_M15pegaGaji_btnOrganisasi', 0, 'Organisasi', 20302);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2030203, 4, 'button_M15pegaGaji_btnGaji', 0, 'Gaji', 20302);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20303, 3, 'menuItem_ParamGajiPeg', 0, 'Parameter Gaji Pegawai', 203);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (204, 2, 'subCat_Payroll_KompKhusus', 0, 'Komponen Khusus', 2);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20401, 3, 'menuItem_M46ppkh', 0, 'Pendapatan/Potongan Khusus', 204);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2040101, 4, 'button_M46ppkh_btnNew', 0, 'New', 20401);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2040102, 4, 'button_M46ppkh_btnEdit', 0, 'Edit', 20401);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2040103, 4, 'button_M46ppkh_btnDelete', 0, 'Delete', 20401);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2040104, 4, 'button_M11ggol_btnListing', 0, 'Listing', 20401);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20402, 3, 'menuItem_T17pdak', 0, 'Pendapatan Akumulasi', 204);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2040201, 4, 'button_T17pdak_btnNew', 0, 'New', 20402);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2040202, 4, 'button_T17pdak_btnEdit', 0, 'Edit', 20402);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2040203, 4, 'button_T17pdak_btnDelete', 0, 'Delete', 20402);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2040204, 4, 'button_T17pdak_btnListing', 0, 'Listing', 20402);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20403, 3, 'menuItem_T23mpeg', 0, 'Mantan Pegawai', 204);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2040301, 4, 'button_T23mpeg_btnNew', 0, 'New', 20403);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2040302, 4, 'button_T23mpeg_btnEdit', 0, 'Edit', 20403);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2040303, 4, 'button_T23mpeg_btnDelete', 0, 'Delete', 20403);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2040304, 4, 'button_T23mpeg_btnListing', 0, 'Listing', 20403);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20404, 3, 'menuItem_Fz2fldaRapel', 0, 'Rapel', 204);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2040401, 4, 'button_Fz2fldaRapel_btnEdit', 0, 'Edit', 20404);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20405, 3, 'menuItem_T19pesa', 0, 'Pesangon', 204);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2040501, 4, 'button_T19pesa_btnEdit', 0, 'Edit', 20405);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2040502, 4, 'button_T19pesa_btnListing', 0, 'Listing', 20405);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (205, 2, 'subCat_Payroll_PendptLain', 0, 'Pendapatan/Potongan Lain-lain', 2);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20501, 3, 'menuItem_T04ttap', 0, 'Tetap', 205);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2050101, 4, 'button_T04ttap_btnNew', 0, 'New', 20501);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2050102, 4, 'button_T04ttap_btnEdit', 0, 'Edit', 20501);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2050103, 4, 'button_T04ttap_btnDelete', 0, 'Delete', 20501);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2050104, 4, 'button_T04ttap_btnListing', 0, 'Listing', 20501);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20502, 3, 'menuItem_T03vari', 0, 'Variable', 205);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2050201, 4, 'button_T03vari_btnNew', 0, 'New', 20502);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2050202, 4, 'button_T03vari_btnEdit', 0, 'Edit', 20502);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2050203, 4, 'button_T03vari_btnDelete', 0, 'Delete', 20502);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2050204, 4, 'button_T03vari_btnListing', 0, 'Listing', 20502);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20503, 3, 'menuItem_T02absn', 0, 'Harian', 205);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2050301, 4, 'button_T02absn_btnNew', 0, 'New', 20503);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2050302, 4, 'button_T02absn_btnEdit', 0, 'Edit', 20503);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2050303, 4, 'button_T02absn_btnDelete', 0, 'Delete', 20503);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2050304, 4, 'button_T02absn_btnListing', 0, 'Listing', 20503);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20504, 3, 'menuItem_M64advh', 0, 'Kelompok Advance', 205);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2050401, 4, 'button_M64advh_btnNew', 0, 'New', 20504);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2050402, 4, 'button_M64advh_btnEdit', 0, 'Edit', 20504);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2050403, 4, 'button_M64advh_btnDelete', 0, 'Delete', 20504);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2050404, 4, 'button_M64advh_btnListing', 0, 'Listing', 20504);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20505, 3, 'menuItem_importDataVar', 0, 'Import Data Variable', 205);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20506, 3, 'menuItem_ExImpDataHarian', 0, 'Import Data Harian', 205);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (206, 2, 'subCat_Payroll_Lembur', 0, 'Lembur', 2);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20601, 3, 'menuItem_Fz1flda', 0, 'Parameter', 206);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2060101, 4, 'button_Fz1flda_btnEdit', 0, 'Edit', 20601);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20602, 3, 'menuItem_M47prlr', 0, 'Range', 206);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2060201, 4, 'button_M47prlr_btnNew', 0, 'New', 20602);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2060202, 4, 'button_M47prlr_btnEdit', 0, 'Edit', 20602);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2060203, 4, 'button_M47prlr_btnDelete', 0, 'Delete', 20602);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2060204, 4, 'button_M47prlr_btnListing', 0, 'Listing', 20602);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20603, 3, 'menuItem_M21umre', 0, 'UMR', 206);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2060301, 4, 'button_M21umre_btnNew', 0, 'New', 20603);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2060302, 4, 'button_M21umre_btnEdit', 0, 'Edit', 20603);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2060303, 4, 'button_M21umre_btnDelete', 0, 'Delete', 20603);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2060304, 4, 'button_M21umre_btnListing', 0, 'Listing', 20603);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20604, 3, 'menuItem_M01areaUMR', 0, 'Area', 206);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2060401, 4, 'button_M01areaUMR_btnEdit', 0, 'Edit', 20604);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2060402, 4, 'button_M01areaUMR_btnListing', 0, 'Listing', 20604);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20605, 3, 'menuItem_T01lemb', 0, 'Lembur', 206);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2060501, 4, 'button_T01lemb_btnNew', 0, 'New', 20605);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2060502, 4, 'button_T01lemb_btnEdit', 0, 'Edit', 20605);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2060503, 4, 'button_T01lemb_btnDelete', 0, 'Delete', 20605);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2060504, 4, 'button_T01lemb_btnListing', 0, 'Listing', 20605);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20606, 3, 'menuItem_ImportLembur', 0, 'Import Data Lembur', 206);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20607, 3, 'menuItem_LapLemburStd', 0, 'Laporan Lembur (Standar)', 206);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20608, 3, 'menuItem_LapLemburNIP', 0, 'Laporan Lembur (Per NIP)', 206);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20609, 3, 'menuItem_LapLemburUker', 0, 'Laporan Lembur (Per Unit Kerja)', 206);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (207, 2, 'subCat_Payroll_Pajak', 0, 'Pajak', 2);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20701, 3, 'menuItem_Fz1fldaPajak', 0, 'Parameter', 207);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2070101, 4, 'button_Fz1fldaPajak_btnEdit', 0, 'Edit', 20701);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20702, 3, 'menuItem_M41jpjk', 0, 'Jenis Pajak', 207);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2070201, 4, 'button_M41jpjk_btnEdit', 0, 'Edit', 20702);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2070202, 4, 'button_M41jpjk_btnListing', 0, 'Listing', 20702);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20703, 3, 'menuItem_M10klaspayroll', 0, 'Klasifikasi Pegawai', 207);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2070301, 4, 'button_M10klaspayroll_btnEdit', 0, 'Edit', 20703);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2070302, 4, 'button_M10klaspayroll_btnListing', 0, 'Listing', 20703);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20704, 3, 'menuItem_T26pphl', 0, 'Pelunasan Pph', 207);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2070401, 4, 'button_T26pphl_btnNew', 0, 'New', 20704);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2070402, 4, 'button_T26pphl_btnEdit', 0, 'Edit', 20704);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2070403, 4, 'button_T26pphl_btnDelete', 0, 'Delete', 20704);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2070404, 4, 'button_T26pphl_btnListing', 0, 'Listing', 20704);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20705, 3, 'menuItem_M45kkhs', 0, 'Karyawan Khusus', 207);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2070501, 4, 'button_M45kkhs_btnNew', 0, 'New', 20705);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2070502, 4, 'button_M45kkhs_btnEdit', 0, 'Edit', 20705);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2070503, 4, 'button_M45kkhs_btnDelete', 0, 'Delete', 20705);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2070504, 4, 'button_M45kkhs_btnListing', 0, 'Listing', 20705);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20706, 3, 'menuItem_T24pdll', 0, 'Pendapatan Lain-lain', 207);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2070601, 4, 'button_T24pdll_btnNew', 0, 'New', 20706);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2070602, 4, 'button_T24pdll_btnEdit', 0, 'Edit', 20706);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2070603, 4, 'button_T24pdll_btnDelete', 0, 'Delete', 20706);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2070604, 4, 'button_T24pdll_btnListing', 0, 'Listing', 20706);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20707, 3, 'menuItem_OPBLA1', 0, 'OPBL SPT-1721 A1', 207);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20708, 3, 'menuItem_OPBLB', 0, 'OPBL SPT-1721 B', 207);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20709, 3, 'menuItem_ImportPph', 0, 'Import Pelunasan Pph', 207);
																							
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (208, 2, 'subCat_Payroll_ESPTLapPjk', 0, 'ESPT & Laporan Pajak', 2);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20801, 3, 'menuItem_ESPT1721_A1', 0, 'SPT 1721-A1', 208);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20802, 3, 'menuItem_ESPT1721_I', 0, 'SPT 1721-I', 208);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20803, 3, 'menuItem_ESPT1721_II', 0, 'SPT 1721-II', 208);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20804, 3, 'menuItem_ESPT1721_T', 0, 'SPT 1721-T', 208);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20805, 3, 'menuItem_ESPTKurangPTKP', 0, 'SPT Kurang dari PTKP', 208);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20806, 3, 'menuItem_ESPTExport1721_A1', 0, 'Export 1721-A1', 208);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20807, 3, 'menuItem_LapPjkPph21', 0, 'Perincian Pph ps.21', 208);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20808, 3, 'menuItem_LapPjkKurangPTKP', 0, 'Pegawai Kurang dari PTKP', 208);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20809, 3, 'menuItem_LapPjkPph21B', 0, 'Perincian Pph ps.21 Form B (Excel)	', 208);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20810, 3, 'menuItem_LapPjkPph21UUsahaCab', 0, 'Perincian Pph ps.21 per U/Usaha & Cabang', 208);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20811, 3, 'menuItem_LapPjkPph21UUsaha', 0, 'Pph-21 per UNIT USAHA', 208);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20812, 3, 'menuItem_LapPjkPph21Cabang', 0, 'Pph-21 per CABANG', 208);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20813, 3, 'menuItem_LapPjkPph21BlnThn', 0, 'Pph ps.21 : Bulan/Tahunan', 208);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20814, 3, 'menuItem_ExportESPT1721_I_Bulan', 0, 'Export SPT 1721 - I Bulanan', 208);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (209, 2, 'subCat_Payroll_ESPTLapPjkPindahCabang', 0, 'ESPT & Laporan Pajak(Pindah Cabang)', 2);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20901, 3, 'menuItem_ESPT1721_A1PindahCabang', 0, 'SPT 1721-A1', 209);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20902, 3, 'menuItem_ESPT1721_IPindahCabang', 0, 'SPT 1721-I', 209);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20903, 3, 'menuItem_ESPT1721_IIPindahCabang', 0, 'SPT 1721-II', 209);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20904, 3, 'menuItem_ESPT1721_TPindahCabang', 0, 'SPT 1721-T', 209);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20905, 3, 'menuItem_ESPTKurangPTKPPindahCabang', 0, 'SPT Kurang dari PTKP', 209);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20906, 3, 'menuItem_ESPTExport1721_A1PindahCabang', 0, 'Export 1721-A1', 209);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20907, 3, 'menuItem_LapPjkPph21PindahCabang', 0, 'Perincian Pph ps.21', 209);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20908, 3, 'menuItem_LapPjkKurangPTKPPindahCabang', 0, 'Pegawai Kurang dari PTKP', 209);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20909, 3, 'menuItem_LapPjkPph21BPindahCabang', 0, 'Perincian Pph ps.21 Form B (Excel)	', 209);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20910, 3, 'menuItem_LapPjkPph21UUsahaCabPindahCabang', 0, 'Perincian Pph ps.21 per U/Usaha & Cabang', 209);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20911, 3, 'menuItem_LapPjkPph21UUsahaPindahCabang', 0, 'Pph-21 per UNIT USAHA', 209);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20912, 3, 'menuItem_LapPjkPph21CabangPindahCabang', 0, 'Pph-21 per CABANG', 209);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (20913, 3, 'menuItem_LapPjkPph21BlnThnPindahCabang', 0, 'Pph ps.21 : Bulan/Tahunan', 209);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (210, 2, 'subCat_Payroll_Jamsostek', 0, 'Jamsostek & Asuransi', 2);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21001, 3, 'menuItem_M18jams', 0, 'Parameter Jamsostek', 210);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2100101, 4, 'button_M18jams_btnNew', 0, 'New', 21001);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2100102, 4, 'button_M18jams_btnEdit', 0, 'Edit', 21001);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2100103, 4, 'button_M18jams_btnDelete', 0, 'Delete', 21001);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2100104, 4, 'button_M18jams_btnListing', 0, 'Listing', 21001);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21002, 3, 'menuItem_M02uusaJKK', 0, 'Unit Usaha', 210);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2100201, 4, 'button_M02uusaJKK_btnNew', 0, 'New', 21002);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2100202, 4, 'button_M02uusaJKK_btnEdit', 0, 'Edit', 21002);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2100203, 4, 'button_M02uusaJKK_btnDelete', 0, 'Delete', 21002);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2100204, 4, 'button_M02uusaJKK_btnListing', 0, 'Listing', 21002);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21003, 3, 'menuItem_M44jasu', 0, 'Jenis Asuransi', 210);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2100301, 4, 'button_M44jasu_btnNew', 0, 'New', 21003);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2100302, 4, 'button_M44jasu_btnEdit', 0, 'Edit', 21003);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2100303, 4, 'button_M44jasu_btnDelete', 0, 'Delete', 21003);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2100304, 4, 'button_M44jasu_btnListing', 0, 'Listing', 21003);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21004, 3, 'menuItem_T22kpas', 0, 'Kepesertaan Asuransi', 210);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2100401, 4, 'button_T22kpas_btnNew', 0, 'New', 21004);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2100402, 4, 'button_T22kpas_btnEdit', 0, 'Edit', 21004);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2100403, 4, 'button_T22kpas_btnDelete', 0, 'Delete', 21004);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2100404, 4, 'button_T22kpas_btnListing', 0, 'Listing', 21004);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21005, 3, 'menuItem_ParamAsuransi', 0, 'Parameter Asuransi', 210);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21006, 3, 'menuItem_PremiAsuransi', 0, 'Premi Asuransi', 210);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21007, 3, 'menuItem_ProsesAsuransi', 0, 'Proses Tunjangan Premi Asuransi', 210);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (211, 2, 'subCat_Payroll_LapJamsostek', 0, 'Laporan Jamsostek & Asuransi', 2);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21101, 3, 'menuItem_LapJamsSummary', 0, 'Summary of Pension', 211);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21102, 3, 'menuItem_LapJamsTenagaKerja', 0, 'Daftar Upah dan Tenaga Kerja', 211);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21103, 3, 'menuItem_LapJamsTenagaKerjaUUsaha', 0, 'Daftar Upah dan Tenaga Kerja per Unit Usaha', 211);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21104, 3, 'menuItem_LapJamsTenagaKerjaCab', 0, 'Daftar Upah dan Tenaga Kerja per Cabang', 211);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21105, 3, 'menuItem_LapJamsMutasi', 0, 'Daftar Upah dan Mutasi Tenaga Kerja', 211);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21106, 3, 'menuItem_LapJamsMutasiCab', 0, 'Daftar Upah dan Mutasi Tenaga Kerja per Cabang', 211);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21107, 3, 'menuItem_LapJamsMutasiPeg', 0, 'Daftar Upah dan Mutasi Tenaga Kerja per Nama Pegawai', 211);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21108, 3, 'menuItem_LapJamsMutasiASTEK', 0, 'Daftar Upah dan Mutasi Tenaga Kerja (ASTEK)', 211);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21109, 3, 'menuItem_LapJamsDftMutasi', 0, 'Daftar Mutasi Upah', 211);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21110, 3, 'menuItem_LapJamsDftTenagaKerja', 0, 'Pendaftaran Tenaga Kerja', 211);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21111, 3, 'menuItem_LapJamsDftKeluar', 0, 'Daftar Tenaga Kerja Keluar', 211);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21112, 3, 'menuItem_LapJamsRincian', 0, 'Rincian Iuran Tenaga Kerja (System Pindah Cabang)', 211);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21113, 3, 'menuItem_LapJamsDataAsuransi', 0, 'Data Peserta Asuransi', 211);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21114, 3, 'menuItem_LapJamsUbahAsuransi', 0, 'Perubahan Data Peserta Asuransi', 211);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21115, 3, 'menuItem_LapJamsExTenagaKerja', 0, 'Export Daftar Upah Tenaga Kerja', 211);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21116, 3, 'menuItem_LapJamsExPensiun', 0, 'Export Dana Pensiun', 211);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21117, 3, 'menuItem_LapJamsExAsuransi', 0, 'Export Iuran Pserta Asuransi', 211);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (212, 2, 'subCat_Payroll_Piutang', 0, 'Piutang', 2);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21201, 3, 'menuItem_M22jnsp', 0, 'Jenis Piutang', 212);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2120101, 4, 'button_M22jnsp_btnNew', 0, 'New', 21201);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2120102, 4, 'button_M22jnsp_btnEdit', 0, 'Edit', 21201);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2120103, 4, 'button_M22jnsp_btnDelete', 0, 'Delete', 21201);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2120104, 4, 'button_M22jnsp_btnListing', 0, 'Listing', 21201);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21202, 3, 'menuItem_T05hpiu', 0, 'Piutang', 212);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2120201, 4, 'button_T05hpiu_btnNew', 0, 'New', 21202);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2120202, 4, 'button_T05hpiu_btnEdit', 0, 'Edit', 21202);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2120203, 4, 'button_T05hpiu_btnDelete', 0, 'Delete', 21202);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2120204, 4, 'button_T05hpiu_btnListing', 0, 'Listing', 21202);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21203, 3, 'menuItem_T07bayp', 0, 'Pembayaran Piutang', 212);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2120301, 4, 'button_T07bayp_btnNew', 0, 'New', 21203);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2120302, 4, 'button_T07bayp_btnEdit', 0, 'Edit', 21203);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2120303, 4, 'button_T07bayp_btnDelete', 0, 'Delete', 21203);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2120304, 4, 'button_T07bayp_btnListing', 0, 'Listing', 21203);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21204, 3, 'menuItem_LapPiutangBayar', 0, 'Lap. Pembayaran Piutang', 212);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21205, 3, 'menuItem_LapPiutangKartu', 0, 'Lap. Kartu Piutang', 212);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21206, 3, 'menuItem_LapPiutangSummary', 0, 'Lap. Summary O/S piutang', 212);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21207, 3, 'menuItem_LapPiutangBelumAngsur', 0, 'Lap. O/S Piutang yang belum ada Angsuran', 212);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21208, 3, 'menuItem_LapPiutangLunas', 0, 'Lap. Piutang yang sudah Lunas', 212); 

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (213, 2, 'subCat_Payroll_Proses', 0, 'Proses', 2);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21301, 3, 'menuItem_ProsesBulan', 0, 'Bulanan', 213);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21302, 3, 'menuItem_ProsesHari', 0, 'Harian', 213);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21303, 3, 'menuItem_ProsesTHR', 0, 'THR', 213);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21304, 3, 'menuItem_ProsesKhusus', 0, 'Payroll Khusus', 213);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21305, 3, 'menuItem_ProsesMantan', 0, 'Mantan Pegawai', 213);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21306, 3, 'menuItem_UnPostPayroll', 0, 'Unpost Bulanan/Harian', 213);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21307, 3, 'menuItem_UnPostKhusus', 0, 'Unpost THR/Khusus/Mantan Pegawai', 213);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (214, 2, 'subCat_Payroll_OutputProses', 0, 'Output Proses Payroll', 2);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21401, 3, 'menuItem_S01hgaj', 0, 'Summary Payroll', 214);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21402, 3, 'menuItem_OutputValid', 0, 'Validation List', 214);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (215, 2, 'subCat_Payroll_Merge', 0, 'Merge to GL', 2);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21501, 3, 'menuItem_M02uusaMerge', 0, 'Unit Usaha', 215);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2150101, 4, 'button_M02uusaMerge_btnEdit', 0, 'Edit', 21501);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2150102, 4, 'button_M02uusaMerge_btnListing', 0, 'Listing', 21501);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21502, 3, 'menuItem_M17ukerMerge', 0, 'Unit Kerja', 215);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2150201, 4, 'button_M17ukerMerge_btnEdit', 0, 'Edit', 21502);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2150202, 4, 'button_M17ukerMerge_btnListing', 0, 'Listing', 21502);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21503, 3, 'menuItem_M08hcabMerge', 0, 'Cabang', 215);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2150301, 4, 'button_M08hcabMerge_btnEdit', 0, 'Edit', 21503);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (2150302, 4, 'button_M08hcabMerge_btnListing', 0, 'Listing', 21503);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21504, 3, 'menuItem_MergeProses', 0, 'Proses Merge', 215);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21505, 3, 'menuItem_MergeUnProses', 0, 'Proses Unmerge', 215);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21506, 3, 'menuItem_MergeInquiry', 0, 'Inquiry Hasil Merge', 215);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21507, 3, 'menuItem_MergeExport', 0, 'Export Hasil Merge', 215);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21508, 3, 'menuItem_MergeProyek', 0, 'Proyek', 215);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21509, 3, 'menuItem_MergeTimeSheet', 0, 'Time Sheet', 215);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21510, 3, 'menuItem_MergeImpTimeSheet', 0, 'Import Data Time Sheet', 215);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21511, 3, 'menuItem_MergeInquiryProyek', 0, 'Inquiry Hasil Merge Proyek', 215);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21512, 3, 'menuItem_MergeLinking', 0, 'Tabel Linking HAPIS to GL', 215);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21513, 3, 'menuItem_MergeExpInterface', 0, 'Export Interface File GL', 215);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (21514, 3, 'menuItem_MergeUpdateFlag', 0, 'Update Flag Capture', 215);

-----------------------------
---------- Payment ----------
-----------------------------
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (3, 1, 'cat_Payment', 0, 'Payment', 0);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (301, 2, 'subCat_Payment_Blank', 0, '[Blank Sub Category]', 3);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (30101, 3, 'menuItem_Fz2fldaPerusahaan', 0, 'Perusahaan', 301);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (3010101, 4, 'button_Fz2fldaPerusahaan_btnEdit', 0, 'Edit', 30101);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (30102, 3, 'menuItem_M14bank', 0, 'Bank Pegawai', 301);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (3010201, 4, 'button_M14bank_btnNew', 0, 'New', 30102);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (3010202, 4, 'button_M14bank_btnEdit', 0, 'Edit', 30102);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (3010203, 4, 'button_M14bank_btnDelete', 0, 'Delete', 30102);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (3010204, 4, 'button_M14bank_btnListing', 0, 'Listing', 30102);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (302, 2, 'subCat_Payment_SlipGaji', 0, 'Slip Gaji', 3);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (30201, 3, 'menuItem_SlipGajiStandar', 0, 'Standard', 302);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (30202, 3, 'menuItem_SlipGajiKecil36', 0, 'Kecil (3.6 inch)', 302);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (30203, 3, 'menuItem_SlipGajiKecil55', 0, 'Kecil (5.5 inch)', 302);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (303, 2, 'subCat_Payment_GajiTunai', 0, 'Gaji Tunai', 3);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (30301, 3, 'menuItem_GajiTunaiAreaCabang', 0, 'per Area/Cabang', 303);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (30302, 3, 'menuItem_GajiTunaiCabUUsaha', 0, 'per Cabang dan Unit Usaha', 303);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (304, 2, 'subCat_Payment_BankTransfer', 0, 'Bank Transfer', 3);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (30401, 3, 'menuItem_BankTransferStandar', 0, 'Standard', 304);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (30402, 3, 'menuItem_BankTransferPeg', 0, 'per Pegawai', 304);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (30403, 3, 'menuItem_BankTransferCabPeg', 0, 'Per Cabang & Pegawai', 304);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (30404, 3, 'menuItem_BankTransferUUKer', 0, 'Per Unit Kerja', 304);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (30405, 3, 'menuItem_BankTransferUUsaha', 0, 'Per Unit Usaha', 304);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (30406, 3, 'menuItem_BankTransferUUsahaCab', 0, 'Per Unit Usaha & Cabang', 304);
										
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (305, 2, 'subCat_Payment_Export', 0, 'Export Data', 3);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (30501, 3, 'menuItem_ExportCIMB', 0, 'CIMB Niaga', 305);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (30502, 3, 'menuItem_ExportHSBC', 0, 'HSBC', 305);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (30503, 3, 'menuItem_ExportComWealth', 0, 'CommonWealth', 305);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (30504, 3, 'menuItem_ExportPermata', 0, 'Permata', 305);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (30505, 3, 'menuItem_ExportBCA', 0, 'BCA', 305);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (30506, 3, 'menuItem_ExportBII', 0, 'BII', 305);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (30507, 3, 'menuItem_ExportBankTokyo', 0, 'Bank of Tokyo', 305);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (30508, 3, 'menuItem_ExportMandiri', 0, 'Mandiri-1', 305);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (30509, 3, 'menuItem_ExportCitibank', 0, 'Citibank', 305);

-----------------------------
---------- Analisa ----------
-----------------------------
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (4, 1, 'cat_Analisa', 0, 'Analisa', 0);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (401, 2, 'subCat_Analisa_Blank', 0, '[Blank Sub Category]', 4);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (40101, 3, 'menuItem_Group', 0, 'Group', 401);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (40102, 3, 'menuItem_NIPGroup', 0, 'NIP - Group', 401);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (402, 2, 'subCat_Analisa_ArsipGaji', 0, 'Arsip Gaji', 4);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (40201, 3, 'menuItem_LapArsipGajiStandar', 0, 'Standard', 402);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (40202, 3, 'menuItem_LapArsipGajiUUsaha', 0, 'Per Unit Usaha', 402);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (40203, 3, 'menuItem_LapArsipGajiUUsahaCab', 0, 'Per Unit Usaha & Cabang', 402);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (40204, 3, 'menuItem_LapArsipGajiTotUUsaha', 0, 'Total per Unit Usaha', 402);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (40205, 3, 'menuItem_LapArsipGajiValuta', 0, 'Valuta', 402);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (403, 2, 'subCat_Analisa_KartuSlip', 0, 'Kartu Slip', 4);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (40301, 3, 'menuItem_LapKartuSlipStandar', 0, 'Standard', 403);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (40302, 3, 'menuItem_LapKartuSlipCabJab', 0, 'Per Cabang & Jabatan', 403);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (40303, 3, 'menuItem_LapKartuSlipUUsaha', 0, 'Per Unit Usaha', 403);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (40304, 3, 'menuItem_LapKartuSlipUUsahaCab', 0, 'Per Unit Usaha & Cabang', 403);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (40305, 3, 'menuItem_LapKartuSlipTotal', 0, 'Total', 403);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (40306, 3, 'menuItem_LapKartuSlipTotalCab', 0, 'Total per Cabang', 403);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (404, 2, 'subCat_Analisa_JournalSalary', 0, 'Journal Salary', 4);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (40401, 3, 'menuItem_JournalSalaryStandar', 0, 'Standard', 404);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (40402, 3, 'menuItem_JournalSalaryGroup', 0, 'Group', 404);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (40403, 3, 'menuItem_JournalSalaryAdvance', 0, 'Advance', 404);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (405, 2, 'subCat_Analisa_Laporan', 0, 'Laporan', 4);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (40501, 3, 'menuItem_LapAnalisaPeg', 0, 'Pegawai Belum Terdefinisi di NIP-Group', 405);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (40502, 3, 'menuItem_LapAnalisaRekon', 0, 'Rekonsiliasi Pendapatan Tetap', 405);

-----------------------------
--------- HRD ---------------
-----------------------------
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5, 1, 'cat_HRD', 0, 'HRD', 0);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (501, 2, 'subCat_HRD_MutasiKarir', 0, 'Mutasi & Karir', 5);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (50101, 3, 'menuItem_M24mutr', 0, 'Alasan Mutasi', 501);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5010101, 4, 'button_M24mutr_btnNew', 0, 'New', 50101);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5010102, 4, 'button_M24mutr_btnEdit', 0, 'Edit', 50101);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5010103, 4, 'button_M24mutr_btnDelete', 0, 'Delete', 50101);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5010104, 4, 'button_M24mutr_btnListing', 0, 'Listing', 50101);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (50102, 3, 'menuItem_T10muta', 0, 'Mutasi & Karir', 501);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5010201, 4, 'button_T10muta_btnNew', 0, 'New', 50102);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5010202, 4, 'button_T10muta_btnEdit', 0, 'Edit', 50102);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5010203, 4, 'button_T10muta_btnDelete', 0, 'Delete', 50102);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5010204, 4, 'button_T10muta_btnListing', 0, 'Listing', 50102);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (50103, 3, 'menuItem_S08muta', 0, 'OPBL Mutasi & Karir', 501);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5010301, 4, 'button_S08muta_btnNew', 0, 'New', 50103);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5010302, 4, 'button_S08muta_btnEdit', 0, 'Edit', 50103);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5010303, 4, 'button_S08muta_btnDelete', 0, 'Delete', 50103);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5010304, 4, 'button_S08muta_btnListing', 0, 'Listing', 50103);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (50104, 3, 'menuItem_ImportMutasiKarir', 0, 'Import Mutasi & Karir', 501);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (502, 2, 'subCat_HRD_Pendidikan', 0, 'Pendidikan', 5);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (50201, 3, 'menuItem_M25jnsd', 0, 'Jenis Pendidikan', 502);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5020101, 4, 'button_M25jnsd_btnNew', 0, 'New', 50201);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5020102, 4, 'button_M25jnsd_btnEdit', 0, 'Edit', 50201);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5020103, 4, 'button_M25jnsd_btnDelete', 0, 'Delete', 50201);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5020104, 4, 'button_M25jnsd_btnListing', 0, 'Listing', 50201);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (50202, 3, 'menuItem_M26jrsn', 0, 'Jurusan Pendidikan', 502);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5020201, 4, 'button_M26jrsn_btnNew', 0, 'New', 50202);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5020202, 4, 'button_M26jrsn_btnEdit', 0, 'Edit', 50202);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5020203, 4, 'button_M26jrsn_btnDelete', 0, 'Delete', 50202);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5020204, 4, 'button_M26jrsn_btnListing', 0, 'Listing', 50202);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (50203, 3, 'menuItem_M27lmbd', 0, 'Lembaga Pendidikan', 502);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5020301, 4, 'button_M27lmbd_btnNew', 0, 'New', 50203);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5020302, 4, 'button_M27lmbd_btnEdit', 0, 'Edit', 50203);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5020303, 4, 'button_M27lmbd_btnDelete', 0, 'Delete', 50203);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5020304, 4, 'button_M27lmbd_btnListing', 0, 'Listing', 50203);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (50204, 3, 'menuItem_M28lksd', 0, 'Lokasi Pendidikan', 502);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5020401, 4, 'button_M28lksd_btnNew', 0, 'New', 50204);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5020402, 4, 'button_M28lksd_btnEdit', 0, 'Edit', 50204);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5020403, 4, 'button_M28lksd_btnDelete', 0, 'Delete', 50204);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5020404, 4, 'button_M28lksd_btnListing', 0, 'Listing', 50204);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (503, 2, 'subCat_HRD_LatBelakang', 0, 'Latar Belakang', 5);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (50301, 3, 'menuItem_M36jbhs', 0, 'Jenis Bahasa', 503);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5030101, 4, 'button_M36jbhs_btnNew', 0, 'New', 50301);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5030102, 4, 'button_M36jbhs_btnEdit', 0, 'Edit', 50301);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5030103, 4, 'button_M36jbhs_btnDelete', 0, 'Delete', 50301);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5030104, 4, 'button_M36jbhs_btnListing', 0, 'Listing', 50301);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (50302, 3, 'menuItem_M34hobi', 0, 'Jenis Hobi', 503);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5030201, 4, 'button_M34hobi_btnNew', 0, 'New', 50302);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5030202, 4, 'button_M34hobi_btnEdit', 0, 'Edit', 50302);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5030203, 4, 'button_M34hobi_btnDelete', 0, 'Delete', 50302);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5030204, 4, 'button_M34hobi_btnListing', 0, 'Listing', 50302);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (504, 2, 'subCat_HRD_Cuti', 0, 'Cuti', 5);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (50401, 3, 'menuItem_Fz1Cuti', 0, 'Parameter Cuti', 504);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5040101, 4, 'button_Fz1Cuti_btnEdit', 0, 'Edit', 50401);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (50402, 3, 'menuItem_M29jnsaCuti', 0, 'Jenis Cuti', 504);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5040201, 4, 'button_M29jnsaCuti_btnNew', 0, 'New', 50402);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5040202, 4, 'button_M29jnsaCuti_btnEdit', 0, 'Edit', 50402);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5040203, 4, 'button_M29jnsaCuti_btnDelete', 0, 'Delete', 50402);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5040204, 4, 'button_M29jnsaCuti_btnListing', 0, 'Listing', 50402);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (50403, 3, 'menuItem_S09hcut', 0, 'Jatah Cuti', 504);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5040301, 4, 'button_S09hcut_btnNew', 0, 'New', 50403);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5040302, 4, 'button_S09hcut_btnEdit', 0, 'Edit', 50403);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5040303, 4, 'button_S09hcut_btnDelete', 0, 'Delete', 50403);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5040304, 4, 'button_S09hcut_btnListing', 0, 'Listing', 50403);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (50404, 3, 'menuItem_T08absn', 0, 'Pemakaian Cuti', 504);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5040401, 4, 'button_T08absn_btnNew', 0, 'New', 50404);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5040402, 4, 'button_T08absn_btnEdit', 0, 'Edit', 50404);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5040403, 4, 'button_T08absn_btnDelete', 0, 'Delete', 50404);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5040404, 4, 'button_T08absn_btnListing', 0, 'Listing', 50404);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (505, 2, 'subCat_HRD_Penghargaan', 0, 'Penghargaan & Peringatan', 5);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (50501, 3, 'menuItem_M30hahu', 0, 'Jenis Penghargaan/Peringatan', 505);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5050101, 4, 'button_M30hahu_btnNew', 0, 'New', 50501);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5050102, 4, 'button_M30hahu_btnEdit', 0, 'Edit', 50501);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5050103, 4, 'button_M30hahu_btnDelete', 0, 'Delete', 50501);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5050104, 4, 'button_M30hahu_btnListing', 0, 'Listing', 50501);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (506, 2, 'subCat_HRD_Medical', 0, 'Medical', 5);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (50601, 3, 'menuItem_M32jmed', 0, 'Jenis Medical', 506);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5060101, 4, 'button_M32jmed_btnNew', 0, 'New', 50601);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5060102, 4, 'button_M32jmed_btnEdit', 0, 'Edit', 50601);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5060103, 4, 'button_M32jmed_btnDelete', 0, 'Delete', 50601);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5060104, 4, 'button_M32jmed_btnListing', 0, 'Listing', 50601);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (50602, 3, 'menuItem_M42tspl', 0, 'Tipe Supplier', 506);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5060201, 4, 'button_M42tspl_btnNew', 0, 'New', 50602);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5060202, 4, 'button_M42tspl_btnEdit', 0, 'Edit', 50602);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5060203, 4, 'button_M42tspl_btnDelete', 0, 'Delete', 50602);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5060204, 4, 'button_M42tspl_btnListing', 0, 'Listing', 50602);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (50603, 3, 'menuItem_M43supl', 0, 'Supplier', 506);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5060301, 4, 'button_M43supl_btnNew', 0, 'New', 50603);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5060302, 4, 'button_M43supl_btnEdit', 0, 'Edit', 50603);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5060303, 4, 'button_M43supl_btnDelete', 0, 'Delete', 50603);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5060304, 4, 'button_M43supl_btnListing', 0, 'Listing', 50603);

INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (50604, 3, 'menuItem_T20jtmj', 0, 'Plafon', 506);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5060401, 4, 'button_T20jtmj_btnNew', 0, 'New', 50604);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5060402, 4, 'button_T20jtmj_btnEdit', 0, 'Edit', 50604);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5060403, 4, 'button_T20jtmj_btnDelete', 0, 'Delete', 50604);
INSERT INTO sec_right ( rig_id, rig_type, rig_name, version, rig_desc, rig_parent_id) VALUES (5060404, 4, 'button_T20jtmj_btnListing', 0, 'Listing', 50604);

-----------------------------
----- Administration --------
-----------------------------
insert into sec_right (rig_id, rig_type, rig_name, rig_desc, rig_parent_id, version) values (99, 1, 'cat_administration', 'Administration', 0, 0);
insert into sec_right (rig_id, rig_type, rig_name, rig_desc, rig_parent_id, version) values (9901, 2, 'subCat_blank', '[Blank Sub Category]', 99, 0);
insert into sec_right (rig_id, rig_type, rig_name, rig_desc, rig_parent_id, version) values (990101, 3, 'menuItem_users', 'Users', 9901, 0);
insert into sec_right (rig_id, rig_type, rig_name, rig_desc, rig_parent_id, version) values (99010101, 4, 'button_users_btnNew', 'New', 990101, 0);
insert into sec_right (rig_id, rig_type, rig_name, rig_desc, rig_parent_id, version) values (99010102, 4, 'button_users_btnEdit', 'Edit', 990101, 0);
insert into sec_right (rig_id, rig_type, rig_name, rig_desc, rig_parent_id, version) values (99010103, 4, 'button_users_btnDelete', 'Delete', 990101, 0);
insert into sec_right (rig_id, rig_type, rig_name, rig_desc, rig_parent_id, version) values (99010104, 4, 'button_users_btnActivate', 'Activate', 990101, 0);
insert into sec_right (rig_id, rig_type, rig_name, rig_desc, rig_parent_id, version) values (99010105, 4, 'button_users_btnSuspend', 'Suspend', 990101, 0);
insert into sec_right (rig_id, rig_type, rig_name, rig_desc, rig_parent_id, version) values (99010106, 4, 'button_users_btnReactivate', 'Reactivate', 990101, 0);
insert into sec_right (rig_id, rig_type, rig_name, rig_desc, rig_parent_id, version) values (99010107, 4, 'button_users_btnListing', 'Listing', 990101, 0);

insert into sec_right (rig_id, rig_type, rig_name, rig_desc, rig_parent_id, version) values (990102, 3, 'menuItem_userRoles', 'User Roles', 9901, 0);
insert into sec_right (rig_id, rig_type, rig_name, rig_desc, rig_parent_id, version) values (99010201, 4, 'button_userRoles_btnSave', 'Save', 990102, 0);

insert into sec_right (rig_id, rig_type, rig_name, rig_desc, rig_parent_id, version) values (990103, 3, 'menuItem_roles', 'Roles', 9901, 0);
insert into sec_right (rig_id, rig_type, rig_name, rig_desc, rig_parent_id, version) values (99010301, 4, 'button_roles_btnNew', 'New', 990103, 0);
insert into sec_right (rig_id, rig_type, rig_name, rig_desc, rig_parent_id, version) values (99010302, 4, 'button_roles_btnEdit', 'Edit', 990103, 0);
insert into sec_right (rig_id, rig_type, rig_name, rig_desc, rig_parent_id, version) values (99010303, 4, 'button_roles_btnDelete', 'Delete', 990103, 0);

insert into sec_right (rig_id, rig_type, rig_name, rig_desc, rig_parent_id, version) values (990104, 3, 'menuItem_roleRights', 'Role Rights', 9901, 0);
insert into sec_right (rig_id, rig_type, rig_name, rig_desc, rig_parent_id, version) values (99010401, 4, 'button_roleRights_btnNew', 'New', 990104, 0);
insert into sec_right (rig_id, rig_type, rig_name, rig_desc, rig_parent_id, version) values (99010402, 4, 'button_roleRights_btnDelete', 'Delete', 990104, 0);

insert into sec_right (rig_id, rig_type, rig_name, rig_desc, rig_parent_id, version) values (990105, 3, 'menuItem_parameter', 'Parameter', 9901, 0);
insert into sec_right (rig_id, rig_type, rig_name, rig_desc, rig_parent_id, version) values (99010501, 4, 'button_parameter_btnSave', 'Save', 990105, 0);
insert into sec_right (rig_id, rig_type, rig_name, rig_desc, rig_parent_id, version) values (990107, 3, 'menuItem_logActivity', 'Log Activity', 9901, 0);
