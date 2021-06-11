package br.com.upperselenium.base.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Random;

import org.joda.time.DateTime;

import br.com.upperselenium.base.exception.RunningException;
import br.com.upperselenium.base.logger.BaseLogger;
import br.com.upperselenium.base.constant.MessagePRM;
import br.com.upperselenium.base.constant.SymbolPRM;

/**
 * Helper class for dynamic number generation.
 *  
 * @author Hudson
 */
public final class RandomDataGeneratorUtil {
	
	private RandomDataGeneratorUtil(){}
	
	/**
	 * This method returns a random number with the length specified by the given parameter.
	 * @param numberSize
	 * @return
	 */	
	public static String generateRandomNumber(int numberSize) {
		Random gerador = new Random();
		String numeroConcatenado = "";
		Integer numero;
		for (int i = 0; i < numberSize; i++) {
			numero = gerador.nextInt(10);
			numeroConcatenado = numeroConcatenado.concat(numero.toString());
		}
		return numeroConcatenado;
	}
	
	/**
	 * This method returns an encrypted string with a length predefined by the given parameter.
	 * @param dataLength
	 * @return
	 */
	public static String generateRandomData(int dataLength){
		Random random = new Random();
		try {
			Thread.sleep(1);
			int dateInMillisNow = DateTime.now().getMillisOfSecond(); 
			String hashSha1 = encryptData(String.valueOf(random.nextInt(9999999)) + String.valueOf(dateInMillisNow));
			String hash = hashSha1.substring(1, dataLength+1);	
			return hash;
		} catch (InterruptedException e) {
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.RANDOM_DATA_PROBLEM), e);
		}
	}
	
	private static String encryptData(String dataToEncrypt){
	    String sha1 = "";
	    try {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(dataToEncrypt.getBytes(SymbolPRM.UTF8));
	        sha1 = byteToHex(crypt.digest());
	    } catch(NoSuchAlgorithmException e){
	    	throw new RunningException(BaseLogger.logException(MessagePRM.AsException.PROBLEM_WHEN_ENCRYPTING), e);
	    } catch(UnsupportedEncodingException e){
	    	throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ENCODING_NOT_SUPPORTED), e);
	    }
	    return sha1;
	}
	
	private static String byteToHex(final byte[] hash){
	    Formatter formatter = new Formatter();
	    for (byte b : hash){
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}
	
	/**
	 * This method is responsible for validating Brazilian CPF numbers.
	 * @param cpf
	 * @return
	 */
	public static boolean validarCPF(String cpf) {
		if (cpf.length() != 11) {
			return false;
		}
		String numDig = cpf.substring(0, 9);
		return calcularDigitoVerificador(numDig).equals(cpf.substring(9, 11));
	}

	/**
	 * This method is responsible for generating Brazilian CPF numbers.
	 * @param cpf
	 * @return
	 */
	public String gerarCPF() {
		StringBuilder iniciais = new StringBuilder();
		for (int i = 0; i < 9; ++i) {
			iniciais.append(Integer.valueOf((int) (Math.random() * 10)));
		}

		return iniciais + calcularDigitoVerificador(iniciais.toString());
	}

	private static String calcularDigitoVerificador(String numero) {

		Integer primDig, segDig;
		int soma = 0, peso = 10;
		for (int i = 0; i < numero.length(); i++) {
			soma += Integer.parseInt(numero.substring(i, i + 1)) * peso--;
		}
		if (soma % 11 == 0 || soma % 11 == 1) {
			primDig = Integer.valueOf(0);
		} else {
			primDig = Integer.valueOf(11 - (soma % 11));
		}
		soma = 0;
		peso = 11;
		for (int i = 0; i < numero.length(); i++) {
			soma += Integer.parseInt(numero.substring(i, i + 1)) * peso--;
		}
		soma += primDig.intValue() * 2;
		if (soma % 11 == 0 || soma % 11 == 1) {
			segDig = Integer.valueOf(0);
		} else {
			segDig = Integer.valueOf(11 - (soma % 11));
		}
		return primDig.toString() + segDig.toString();
	}
	
	
	
}
