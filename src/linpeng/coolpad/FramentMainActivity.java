package linpeng.coolpad;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

public class FramentMainActivity extends FragmentActivity {

	private Fragment[] fragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_frament_main);

		fragments = new Fragment[4];
		fragments[0] = getSupportFragmentManager().findFragmentById(R.id.farment_main);
		fragments[1] = getSupportFragmentManager().findFragmentById(R.id.farment_phone);
		fragments[2] = getSupportFragmentManager().findFragmentById(R.id.farment_accessory);
		fragments[3] = getSupportFragmentManager().findFragmentById(R.id.farment_cart);
		getSupportFragmentManager().beginTransaction().
		hide(fragments[1]).hide(fragments[2]).hide(fragments[3]).show(fragments[0]).commit();

	}

	public void mainClick(View view){
		getSupportFragmentManager().beginTransaction().hide(fragments[1]).hide(fragments[2]).hide(fragments[3]).show(fragments[0]).commit();
	}
	public void phoneClick(View view){
		getSupportFragmentManager().beginTransaction().hide(fragments[0]).hide(fragments[2]).hide(fragments[3]).show(fragments[1]).commit();
	}
	public void accessoryClick(View view){
		getSupportFragmentManager().beginTransaction().hide(fragments[0]).hide(fragments[1]).hide(fragments[3]).show(fragments[2]).commit();
	}
	public void cartClick(View view){
		getSupportFragmentManager().beginTransaction().hide(fragments[0]).hide(fragments[1]).hide(fragments[2]).show(fragments[3]).commit();
	}
}
