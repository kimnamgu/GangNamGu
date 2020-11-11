// ����������(Base64Encode / ���������ϸ���> SVRB551982002_env.cer)
var ServerCert	= "MIID5jCCAs6gAwIBAgIQSrGbyANcWJ8ynHDrJ1/wOzANBgkqhkiG9w0BAQUFADBQMQswCQYDVQQGEwJLUjEcMBoGA1UEChMTR292ZXJubWVudCBvZiBLb3JlYTENMAsGA1UECxMER1BLSTEUMBIGA1UEAxMLQ0ExMzEwMDAwMDEwHhcNMDkwOTE3MDIxNTM2WhcNMTExMjE3MDIxNTM2WjBdMQswCQYDVQQGEwJLUjEcMBoGA1UECgwTR292ZXJubWVudCBvZiBLb3JlYTEYMBYGA1UECwwPR3JvdXAgb2YgU2VydmVyMRYwFAYDVQQDDA1TVlJCNTUxOTgyMDAyMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCubnQuwtCXJ4PBhiGaum6w7X+uCQCzCGI2RxVBFXypNHURKGza10jp85Ro3rQLI+hjD3RzI6iyXXjond0cZky5d5eol0Rl5b5WFHkIiUlOWX0lDlFAmd1m5abyjIAsBxONklxLPfdV9a4DIpt9/niA3au1DaUQjSIJBaumptvJRQIDAQABo4IBMTCCAS0wHwYDVR0jBBgwFoAUAc8XrgSNh8y3VQtxbGtInOaxspUwHQYDVR0OBBYEFDOoKjHABOWz5MF8+o0jdBHUVGQTMA4GA1UdDwEB/wQEAwIFIDAYBgNVHSAEETAPMA0GCSqDGoaNIQIBAjAAMIGIBgNVHR8EgYAwfjB8oHqgeIZ2bGRhcDovL2Nlbi5kaXIuZ28ua3I6Mzg5L2NuPWNybDAwMjUsY249Q0ExMzEwMDAwMDEsb3U9R1BLSSxvPUdvdmVybm1lbnQgb2YgS29yZWEsYz1LUj9jZXJ0aWZpY2F0ZVJldm9jYXRpb25saXN0O2JpbmFyeTA2BggrBgEFBQcBAQQqMCgwJgYIKwYBBQUHMAGGGmh0dHA6Ly9ndmEuZ3BraS5nby5rcjo4MDgwMA0GCSqGSIb3DQEBBQUAA4IBAQBnj4Zb/yL85oEnLOP17Cx899F80/MU9/2irrXbVx2GI8lWJQ3idNGIKXqxQUfxWjABYZDKy2eFyZL7SwXJ6RPCXpayYblIFs8td2RT8Cu8vUEqlh44zSDl9YDVoYudJWu8aVTiZ/yoEdtgkEsQacTOevDtjt9YnUk87lrGiF3qI34RaFiw94/GhDd2WUl0f3qhgw+HZzA6zXo9nozhlliY9lFHKEy6p5hVKgAhMcMIJex/09B+NtFG4KBrqxLKBJH+U5lkxJifeI5BoZTWathvEtxnoJBKUH/6xy2NdDziFIqieZSIfjGZiAp9n5nSlhXQDFHck4QBYVTaz1p/XmxS";
var ServerAddr = "127.0.0.1:7001";
var SiteID = "ejms." + ServerAddr;  // SiteID : ���������� ȹ���ϴ� Ű��
var ServiceStartPageURL = "/loginYN.do";
var AlgoMode = 0x30;  // �Ϻ�ȣ �˰��� (0x20 : SYM_ALG_3DES_CBC,
                      // 0x30 : SYM_ALG_SEED_CBC, 0x40 : SYM_ALG_NEAT_CBC,
                      // 0x50 : SYM_ALG_ARIA_CBC, 0x60 : SYM_ALG_NES_CBC)
var WorkDir = "GPKISecureWeb";	// �۾����丮(��� ��ġ�� ��ġ)
var GNCertType = 0x01;		// GPKI, NPKI ������ ��� : 0x00, GPKI �� : 0x01, NPKI �� :0x02
var ValidCertInfo = "";			// Ư���������� �ε� �� ��쿡 ��å�ڵ带 �����Ѵ�.
var ReadCertType = 0x01;		// ����������� : 0x01, ��ȣŰ�й�� ������ : 0x02
var CertOption = 0x01;		// 0x00 : �α����� �����������θ� �����Ѵ�. (�ش��������� �ε��Ѵ�.)
var KeyStrokeType = 0x00;		// Ű���� ���� API : 0x00 : �������
var UbikeyVersion = "1.0.4.5";
var UbikeyPopupURL = "http://www.gpki.go.kr/wire/infovine/download.html";
var UbikeyWParam = "MOPAS";
var UbikeylParam = "DREAMSECURITY|KEYBOARD_SECURITY_COMP_CODE";
var ConfigFilePath = "/gpkisecureweb/setup/setup.conf";
var SetupOffLineFilePath = "/gpkisecureweb/setup/install_off_v1.0.4.1.exe";
var Object_GPKIInstaller = "<OBJECT ID='GPKIInstaller' CLASSID='CLSID:531BBB4D-B043-4D70-8A88-0A416C7F7CD0' width='0' height='0' "
                         + "CODEBASE='http://" + ServerAddr + "/gpkisecureweb/setup/GPKIInstaller.cab#version=1.0.4.1'></OBJECT>";