package ma.ensa.Models;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ma.ensa.Utils.DBConfig;

public class Commande {
	int code;
	Client client;
	Date date;
	List<Article> articles;

	public Commande() {
	}

	public static int passerCommande(List<Article> panier, Client client) {
		DBConfig dbConfig = new DBConfig();
		try {
			String query = "INSERT INTO `commandes` (`CodeClient`) VALUES (?) ";

			PreparedStatement ps = dbConfig.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, client.getCode());

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			int codeCommande = rs.getInt(1);

			for (Article article : panier) {

				query = "INSERT INTO `lignecommande` (`NumCommande`, `CodeArticle`, `QteCde`) VALUES (?,?,?) ";
				ps = dbConfig.getConnection().prepareStatement(query);

				ps.setInt(1, codeCommande);
				ps.setInt(2, article.getCode());
				// To be changed
				ps.setInt(3, 1);

				ps.executeUpdate();

			}
			return codeCommande;

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			dbConfig.close();
		}
	}

	public static List<Commande> getCommandes(Client client) {
		DBConfig dbConfig = new DBConfig();
		try {
			String query = "SELECT DISTINCT commandes.NumCommande, DateCommande FROM commandes JOIN lignecommande on commandes.NumCommande=lignecommande.NumCommande WHERE CodeClient=?";

			PreparedStatement ps = dbConfig.getConnection().prepareStatement(query);
			ps.setInt(1, client.getCode());
			ResultSet rs = ps.executeQuery();
			List<Commande> commandes = new ArrayList<>();
			// Loop over commands
			while (rs.next()) {
				int codeCommande = rs.getInt("NumCommande");
				Commande commande = new Commande();
				commande.setCode(codeCommande);
				commande.setClient(client);
				commande.setDate(rs.getDate("DateCommande"));
				try {
					query = "SELECT Designation, Prix FROM commandes JOIN lignecommande on commandes.NumCommande=lignecommande.NumCommande JOIN articles on lignecommande.CodeArticle=articles.CodeArticle WHERE commandes.NumCommande=?";

					ps = dbConfig.getConnection().prepareStatement(query);
					ps.setInt(1, codeCommande);
					ResultSet rset = ps.executeQuery();
					List<Article> articles = new ArrayList<>();
					// Loop over single command
					while (rset.next()) {
						Article article = new Article();
						article.setDesignation(rset.getString("Designation"));
						article.setPrix(rset.getInt("Prix"));
						articles.add(article);
					}
					commande.setArticles(articles);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				commandes.add(commande);
			}
			return commandes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			dbConfig.close();
		}
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String toString() {
		return "Commande [code=" + code + ", client=" + client + ", date=" + date + ", articles=" + articles + "]";
	}

}
