package net.renfei.utils.wechat.mp;

@SuppressWarnings("serial")
public class AesException extends Exception {

	public static final int OK = 0;
	public static final int VALIDATE_SIGNATURE_ERROR = -40001;
	public static final int PARSE_XML_ERROR = -40002;
	public static final int COMPUTE_SIGNATURE_ERROR = -40003;
	public static final int ILLEGAL_AES_KEY = -40004;
	public static final int VALIDATE_APPID_ERROR = -40005;
	public static final int ENCRYPT_AES_ERROR = -40006;
	public static final int DECRYPT_AES_ERROR = -40007;
	public static final int ILLEGAL_BUFFER = -40008;
//	public static final int ENCODE_BASE_64_ERROR = -40009;
//	public static final int DECODE_BASE_64_ERROR = -40010;
//	public static final int GEN_RETURN_XML_ERROR = -40011;

	private int code;

	private static String getMessage(int code) {
		switch (code) {
		case VALIDATE_SIGNATURE_ERROR:
			return "签名验证错误";
		case PARSE_XML_ERROR:
			return "xml解析失败";
		case COMPUTE_SIGNATURE_ERROR:
			return "sha加密生成签名失败";
		case ILLEGAL_AES_KEY:
			return "SymmetricKey非法";
		case VALIDATE_APPID_ERROR:
			return "appid校验失败";
		case ENCRYPT_AES_ERROR:
			return "aes加密失败";
		case DECRYPT_AES_ERROR:
			return "aes解密失败";
		case ILLEGAL_BUFFER:
			return "解密后得到的buffer非法";
//		case EncodeBase64Error:
//			return "base64加密错误";
//		case DecodeBase64Error:
//			return "base64解密错误";
//		case GenReturnXmlError:
//			return "xml生成失败";
		default:
			return null; // cannot be
		}
	}

	public int getCode() {
		return code;
	}

	AesException(int code) {
		super(getMessage(code));
		this.code = code;
	}

}
