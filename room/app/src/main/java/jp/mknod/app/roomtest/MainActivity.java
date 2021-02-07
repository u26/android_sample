package jp.mknod.app.roomtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

	AppDatabase db;
	UserDao dao;
	EditText edit_last;
	EditText edit_first;
	Button bt_insert;
	Button bt_select;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		db = Room.databaseBuilder(getApplicationContext(),
			AppDatabase.class, "database-name").build();
		dao = db.userDao();

		bt_insert = findViewById(R.id.bt_insert);
		bt_select = findViewById(R.id.bt_select);
		edit_first = findViewById(R.id.edit_first);
		edit_last = findViewById(R.id.edit_last);

		bt_insert.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				new Thread(new Runnable() {
					@Override
					public void run() {
						User u = new User();
						u.firstName = edit_first.getText().toString();
						u.lastName = edit_last.getText().toString();
						dao.insertAll(u);
					}
				}).start();
			}
		});

		bt_select.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				new Thread(new Runnable() {
					@Override
					public void run() {

						List<User> list;
						list = dao.getAll();
						Log.d("ROOM_TEST", "Size=" + Integer.valueOf(list.size()) );

						for(int i=0; i< list.size(); i++) {
							Log.d("AAA", list.get(i).firstName );
						}
					}
				}).start();

			}
		});
	}
}

