package hr.foi.air.main;

import hr.foi.air.db.Difficulties;
import hr.foi.air.db.Modules;
import hr.foi.air.db.Users;

import java.util.List;

import com.gc.materialdesign.views.ButtonRectangle;

import air.testmathfun.R;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;


public class MainActivity extends BaseActivity implements OnClickListener {
	private ButtonRectangle btIgraj,btPravila,btRezultati;
	private List<Difficulties> listaRazina;
	private List<Modules> listaModula;
	private Intent intent;
	
	/**
	 * Metoda koja povezuje layout 
	 */
	@Override
	public int getLayout() {
		return R.layout.activity_main;
	}

	/**
	 * Metoda koja se izvr�ava nakon povezivanja koda s layoutom
	 */
	@Override
	public void initView() {
		btIgraj = (ButtonRectangle) findViewById(R.id.btIgraj);
		btPravila = (ButtonRectangle) findViewById(R.id.btPravila);
		btRezultati=(ButtonRectangle) findViewById(R.id.btRezultati);
		btIgraj.setOnClickListener(this);			
		btPravila.setOnClickListener(this);
		btRezultati.setOnClickListener(this);

		
		prepareDatabase();
	}
	
	/**
	 * Metoda koja postavlja bazu ukoliko je ona prazna (da ima najmanje 4 igra�a, radi ljep�e vizualizacije). Tako�er stvara te�ine i module u bazi.
	 */
	private void prepareDatabase() {
		if (Users.getNumberOfPlayers() == 0) {
			Users user1=new Users("Antonio Markoc", 19);
			user1.save();
			Users user2=new Users("Matija Nedjeral", 12);
			user2.save();
			Users user3=new Users("Borna Farkas", 17);
			user3.save();		
			Users user4=new Users("Mislav Santek", 15);
			user4.save();		
		}
		
		if(Difficulties.getAllDifficulties().size() != Difficulties.createDifficultiesList().size()){
			Difficulties.deleteDifficulties();
			listaRazina=Difficulties.createDifficultiesList();
			for (int i = 0; i < listaRazina.size(); i++) {
				listaRazina.get(i).save();
			}
		}
		
		if(Modules.getAllModules().size() != Modules.createModulesList().size()){
			Modules.deleteModules();
			listaModula=Modules.createModulesList();
			for (int i = 0; i < listaModula.size(); i++) {
				listaModula.get(i).save();
			}
		}
		
	}

	/**
	 * Metoda za postavlanje menua
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Metoda za doga�aj kada je kliknuto ne�to iz menua
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Metoda koja oslu�kuje klik na odre�eni button
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btIgraj:
			startActivity(new Intent(getBaseContext(), Nickname.class));
			break;
		case R.id.btPravila:
			startActivity(new Intent(getBaseContext(), Rules.class));
			break;
		case R.id.btRezultati:
			intent=new Intent(getBaseContext(), Result.class);
			intent.putExtra("sviRezultati", 1);
			startActivity(intent);
			break;
		default:
			break;
		}
		
	}

	/**
	 * Metoda koja kontrolira klik na back tipku. U ovom slu�aje izlazi iz aplikacije.
	 */
	@Override
	public void onBackPressed() {
		//exit application
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}
