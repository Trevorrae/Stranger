package com.mcmaster.stranger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class Help extends Activity {

	// Define variables
	Firebase myFirebase;
	Button AnswerEnter;

	ArrayList<String> list = new ArrayList<String>();
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);

		// Send basic info to Firebase server
		Firebase.setAndroidContext(this);

		// creats the button and button listern
		AnswerEnter = (Button) findViewById(R.id.textenter);
		connectionFireBase();

		/*
		 * if (((AdapterView<SpinnerAdapter>)
		 * findViewById(R.id.question)).getSelectedItem
		 * ().toString()=="Questions:") { System.out.println("test"); } else {
		 * writeQuestionFireBase(); }
		 */

		AnswerEnter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

				inputManager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				// TODO Auto-generated method stub
				writeAnswerFireBase();
				// writeQuestionFireBase();
			}
		});

		final Spinner question = (Spinner) findViewById(R.id.question);

		question.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				writeQuestionFireBase();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		final Context context = getApplicationContext();

		myFirebase.child("User1_Answer").addValueEventListener(
				new ValueEventListener() {

					@Override
					public void onDataChange(DataSnapshot arg0) {
						String UAnswer = arg0.getValue().toString();

						final ListView listview = (ListView) findViewById(R.id.message_view);

						adapter = new ArrayAdapter<String>(context,
								android.R.layout.simple_list_item_1, list);

						adapter.insert(UAnswer,0);

						listview.setAdapter(adapter);
						adapter.notifyDataSetChanged();

					}

					@Override
					public void onCancelled(FirebaseError arg0) {
						// TODO Auto-generated method stub

					}
				});

		myFirebase.child("User1_Question").addValueEventListener(
				new ValueEventListener() {

					@Override
					public void onDataChange(DataSnapshot arg0) {
						String UQuestion = arg0.getValue().toString();

						final ListView listview = (ListView) findViewById(R.id.message_view);

						adapter = new ArrayAdapter<String>(context,
								android.R.layout.simple_list_item_1, list);

						adapter.insert(UQuestion,0);
						listview.setAdapter(adapter);
						adapter.notifyDataSetChanged();

					}

					@Override
					public void onCancelled(FirebaseError arg0) {
						// TODO Auto-generated method stub

					}
				});

		/*
		 * .addChildEventListener( new ChildEventListener() {
		 * 
		 * @Override public void onChildAdded(DataSnapshot arg0, String arg1) {
		 * // TODO Auto-generated method stub
		 * 
		 * String test = arg1;
		 * 
		 * final ListView listview = (ListView) findViewById(R.id.message_view);
		 * adapter = new ArrayAdapter<String>(context,
		 * android.R.layout.simple_list_item_1, list);
		 * 
		 * list.add(test); adapter.notifyDataSetChanged();
		 * listview.setAdapter(adapter);
		 * 
		 * }
		 * 
		 * @Override public void onCancelled(FirebaseError arg0) { // TODO
		 * Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void onChildChanged(DataSnapshot arg0, String arg1)
		 * { // TODO Auto-generated method stub
		 * 
		 * 
		 * }
		 * 
		 * @Override public void onChildMoved(DataSnapshot arg0, String arg1) {
		 * // TODO Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void onChildRemoved(DataSnapshot arg0) { //
		 * TODOAuto-generated method stub
		 * 
		 * } });
		 */

	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.help, menu); return true; }
	 */

	public void connectionFireBase() {
		// Reads and Write connection with Firebase web site

		// (https://stranger.firebaseio.com/
		myFirebase = new Firebase("https://stranger.firebaseio.com/");
		myFirebase.child("User1_Answer").setValue("");
		// readFireBase();

	}

	public void writeAnswerFireBase() {

		final EditText answer = (EditText) findViewById(R.id.answer);

		// final ListView listview = (ListView) findViewById(R.id.message_view);
		String Ans = answer.getText().toString();
		/*
		 * adapter = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_list_item_1, list);
		 */

		// Writes data to Firebase
		myFirebase.child("User1_Answer").setValue(Ans);

		// creats a list of answer to the data base

		/*
		 * Map<String, String> post1 = new HashMap<String, String>();
		 * post1.put("User Answer", Ans);
		 * myFirebaseRef.child("Answer").push().setValue(post1);
		 */

		// list.add(Ans);
		answer.setText("");
		/*
		 * adapter.notifyDataSetChanged(); listview.setAdapter(adapter);
		 */

	}

	public void writeQuestionFireBase() {

		final Spinner question = (Spinner) findViewById(R.id.question);
		// final ListView listview = (ListView) findViewById(R.id.message_view);
		/*
		 * adapter = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_list_item_1, list);
		 */
		String Ques = question.getSelectedItem().toString();

		// Spinner.

		// Writes data to Firebase
		myFirebase.child("User1_Question").setValue(Ques);

		// creats a list of answer to the data base

		/*
		 * Map<String, String> post1 = new HashMap<String, String>();
		 * post1.put("User Question", Ques);
		 * myFirebaseRef.child("Question").push().setValue(post1);
		 */

		/*
		 * list.add(Ques); adapter.notifyDataSetChanged();
		 * listview.setAdapter(adapter);
		 */

	}

	public void readFireBase() {

		// Reads data from Firebase
		/*
		 * myFirebaseRef.child("message_user_1").setValue (ListView.);
		 */
		/*
		 * myFirebaseRef.child("message_user_1").addValueEventListener(new
		 * ValueEventListener() {
		 * 
		 * @Override public void onDataChange(DataSnapshot snapshot) {
		 * System.out.println(snapshot.getValue()); //prints
		 * "Do you have data? You'll love Firebase."
		 * 
		 * }
		 * 
		 * @Override public void onCancelled(FirebaseError error) { }
		 * 
		 * });
		 */

	}

}
