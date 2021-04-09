package ma.ensa.Models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import ma.ensa.Utils.DBConfig;

public class Client {
	String email, mdp, nom, prenom, adresse, ville, telephone;
	int zip, code;

	public Client() {

	}

	public void saveClient() throws Exception {
		DBConfig dbConfig = new DBConfig();
		try {
			String query = "INSERT INTO `clients` (`Email`, `Nom`, `Prenom`, `Adresse`, `CodePostal`, `Ville`, `Tel`, `MotPasse`) VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";

			PreparedStatement ps = dbConfig.getConnection().prepareStatement(query);
			ps.setString(1, this.getEmail());
			ps.setString(2, this.getNom());
			ps.setString(3, this.getPrenom());
			ps.setString(4, this.getAdresse());
			ps.setInt(5, this.getZip());
			ps.setString(6, this.getVille());
			ps.setString(7, this.getTelephone());
			ps.setString(8, this.getMdp());

			ps.executeUpdate();
		} catch (MySQLIntegrityConstraintViolationException e) {
			throw new Exception("duplicate");
		} finally {
			dbConfig.close();
		}
	}

	public static Client getClient(String email, String mdp) {
		DBConfig dbConfig = new DBConfig();
		try {
			String query = "SELECT * FROM clients WHERE email = ? and MotPasse = ?";

			PreparedStatement ps = dbConfig.getConnection().prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, mdp);
			ResultSet rs = ps.executeQuery();
			Client client = new Client();
			while (rs.next()) {
				client.setCode(rs.getInt("Id"));
				client.setNom(rs.getString("Nom"));
				client.setPrenom(rs.getString("Prenom"));
				client.setEmail(rs.getString("Email"));
				client.setMdp(rs.getString("MotPasse"));
				client.setAdresse(rs.getString("Adresse"));
				client.setVille(rs.getString("Ville"));
				client.setZip(rs.getInt("CodePostal"));
				client.setTelephone(rs.getString("Tel"));
				return client;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			dbConfig.close();
		}
		return null;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom.toUpperCase();
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

}
