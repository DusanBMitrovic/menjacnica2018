package menjacnica.gui.kontroler;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

import menjacnica.Menjacnica;
import menjacnica.MenjacnicaInterface;
import menjacnica.Valuta;
import menjacnica.gui.DodajKursGUI;
import menjacnica.gui.IzvrsiZamenuGUI;
import menjacnica.gui.MenjacnicaGUI;
import menjacnica.gui.ObrisiKursGUI;
import menjacnica.gui.models.MenjacnicaTableModel;

public class KontrolerGUI {

	public static MenjacnicaInterface sistem = new Menjacnica();

	public static MenjacnicaGUI mGUI;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KontrolerGUI.mGUI = new MenjacnicaGUI();
					mGUI.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static String izvrsiZamenu(boolean selected, Valuta valuta, double parseDouble, JPanel contentPane) {
		try {
			double konacniIznos = sistem.izvrsiTransakciju(valuta, selected, parseDouble);

			return "" + konacniIznos;

		} catch (Exception e1) {
			JOptionPane.showMessageDialog(contentPane, e1.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	public static void prikaziSveValute(JTable table) {

		MenjacnicaTableModel model = (MenjacnicaTableModel) (table.getModel());

		model.staviSveValuteUModel(sistem.vratiKursnuListu());

	}

	public static void unesiKurs(String naziv, String sN, int sifra, double prodajni, double kupovni, double srednji,
			JPanel contentPane) {
		try {
			Valuta valuta = new Valuta();

			// Punjenje podataka o valuti
			valuta.setNaziv(naziv);
			valuta.setSkraceniNaziv(sN);
			valuta.setSifra(sifra);
			valuta.setProdajni(prodajni);
			valuta.setKupovni(kupovni);
			valuta.setSrednji(srednji);

			// Dodavanje valute u kursnu listu
			sistem.dodajValutu(valuta);

			// Osvezavanje glavnog prozora
			prikaziSveValute(mGUI.getTable());

			// Zatvaranje DodajValutuGUI prozora
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(contentPane, e1.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void obrisiValutu(Valuta valuta, JPanel contentPane) {
		try {
			sistem.obrisiValutu(valuta);

			prikaziSveValute(mGUI.getTable());
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(contentPane, e1.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void prikaziIzvrsiZamenuGUI(JTable table, JPanel contentPane) {
		if (table.getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel) (table.getModel());
			IzvrsiZamenuGUI prozor = new IzvrsiZamenuGUI(mGUI, model.vratiValutu(table.getSelectedRow()));
			prozor.setLocationRelativeTo(contentPane);
			prozor.setVisible(true);
		}
	}

	public static void prikaziObrisiKursGUI(JTable table, JPanel contentPane) {

		if (table.getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel) (table.getModel());
			ObrisiKursGUI prozor = new ObrisiKursGUI(mGUI, model.vratiValutu(table.getSelectedRow()));
			prozor.setLocationRelativeTo(contentPane);
			prozor.setVisible(true);
		}
	}

	public static void prikaziDodajKursGUI(JPanel contentPane) {
		DodajKursGUI prozor = new DodajKursGUI(mGUI);
		prozor.setLocationRelativeTo(contentPane);
		prozor.setVisible(true);
	}

	public static void ucitajIzFajla(JPanel contentPane,JTable table) {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(contentPane);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				sistem.ucitajIzFajla(file.getAbsolutePath());
				KontrolerGUI.prikaziSveValute(table);
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(contentPane, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void sacuvajUFajl(JPanel contentPane) {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(contentPane);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				sistem.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(contentPane, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	

}
