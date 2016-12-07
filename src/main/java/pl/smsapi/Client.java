package pl.smsapi;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import pl.smsapi.exception.ClientException;

public class Client {

	protected String username;
	protected String password;
	protected String token;

	private Client() {}

	public Client(String username) throws ClientException {
		setUsername(username);
	}

	public static Client createFromToken(String token) {
		Client client = new Client();
		client.setToken(token);

		return client;
	}

	public void setUsername(String username) throws ClientException {

		if (username == null || username.isEmpty()) {
			throw new ClientException("Username can not be empty");
		}

		this.token = null;
		this.username = username;
	}

	public void setPasswordHash(String password) throws ClientException {

		if (password == null || password.isEmpty()) {
			throw new ClientException("Password can not be empty");
		}

		this.token = null;
		this.password = password;
	}

	public void setPasswordRAW(String password) throws ClientException {

        String hashPassword = Client.MD5Digest(password);

		setPasswordHash(hashPassword);
	}

	public void setToken(String token) {
		this.username = null;
		this.password = null;
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getToken() {
		return token;
	}

	public static String MD5Digest(String str) throws ClientException {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			md.update(str.getBytes());

			byte byteData[] = md.digest();

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}

			return sb.toString();

		} catch (NoSuchAlgorithmException ex) {
			throw new ClientException(ex.getMessage());
		}
	}
}
