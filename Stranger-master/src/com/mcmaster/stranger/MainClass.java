package com.mcmaster.stranger;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.Firebase.AuthResultHandler;
import com.firebase.client.FirebaseError;
import com.firebase.client.AuthData;
import com.facebook.widget.LoginButton;
import com.firebase.client.FirebaseError;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;



import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.app.ProgressDialog;

public class MainClass extends Activity {

	
	private static final Logger log = Logger.getLogger( MainClass.class.getName() );
	private AuthData authData;
	Button LogIN,Rules,Help;
    ProfilePictureView profilePictureView;
    GraphUser user;
    private static final String TAG = "LoginDemo";
    private ProgressDialog mAuthProgressDialog;
    private Firebase myFirebaseRef;
    private LoginButton fbLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(android.os.Build.VERSION.SDK_INT>9){
        	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
        	.permitAll().build();
        	StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.main_class);
        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://stranger.firebaseio.com/");
        LoginButton fbLogin = (LoginButton)findViewById(R.id.login_button);
        makeGetRequest();
        //makePostRequest();
        fbLogin.setSessionStatusCallback(new Session.StatusCallback() {
        

	@Override
	public void call(Session session, SessionState state, Exception exception) {
		onFacebookSessionStateChange(session, state, exception);
		// TODO Auto-generated method stub
		
	}
   });

        Rules=(Button)findViewById(R.id.Button_Rules);
        Help=(Button)findViewById(R.id.Button_Help);
        final Intent openRULES=new Intent(MainClass.this,Rules.class);
        final Intent openHELP=new Intent(MainClass.this,Help.class);

        
    	Rules.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			startActivity(openRULES);
    		}
    	});
    	
    	final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Please Make Sure you have signed into facebook!");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Yes",
        		
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
    	Help.setOnClickListener(new OnClickListener() {
    		int attemptOne =0;
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			if (attemptOne != 0){
    			//postData("Of course, in one sense, the first essential for a man’s being a good citizen is his possession of the home virtues of which we think when we call a man by the emphatic adjective of manly. No man can be a good citizen who is not a good husband and a good father, who is not honest in his dealings with other men and women, faithful to his friends and fearless in the presence of his foes, who has not got a sound heart, a sound mind, and a sound body; exactly as no amount of attention to civil duties will save a nation if the domestic life is undermined, or there is lack of the rude military virtues which alone can assure a country’s position in the world. In a free republic the ideal citizen must be one willing and able to take arms for the defense of the flag, exactly as the ideal citizen must be the father of many healthy children. A race must be strong and vigorous; it must be a race of good fighters and good breeders, else its wisdom will come to naught and its virtue be ineffective; and no sweetness and delicacy, no love for and appreciation of beauty in art or literature, no capacity for building up material prosperity can possibly atone for the lack of the great virile virtues. But this is aside from my subject, for what I wish to talk of is the attitude of the American citizen in civic life. It ought to be axiomatic in this country that every man must devote a reasonable share of his time to doing his duty in the Political life of the community. No man has a right to shirk his political duties under whatever plea of pleasure or business; and while such shirking may be pardoned in those of small cleans it is entirely unpardonable in those among whom it is most common–in the people whose circumstances give them freedom in the struggle for life. In so far as the community grows to think rightly, it will likewise grow to regard the young man of means who shirks his duty to the State in time of peace as being only one degree worse than the man who thus shirks it in time of war. A great many of our men in business, or of our young men who are bent on enjoying life (as they have a perfect right to do if only they do not sacrifice other things to enjoyment), rather plume themselves upon being good citizens if they even vote; yet voting is the very least of their duties, Nothing worth gaining is ever gained without effort. You can no more have freedom without striving and suffering for it than you can win success as a banker or a lawyer without labor and effort, without self-denial in youth and the display of a ready and alert intelligence in middle age. The people who say that they have not time to attend to politics are simply saying that they are unfit to live in a free community.");
    			startActivity(openHELP);
    			}else{
    				 AlertDialog alert11 = builder1.create();
    		            alert11.show();
    		            attemptOne = 1;
    			}
    		}
    	});
  }
        

      private class AuthResultHandler implements Firebase.AuthResultHandler {

          private final String provider;

          public AuthResultHandler(String provider) {
              this.provider = provider;
          }

          @Override
          public void onAuthenticated(AuthData authData) {
              mAuthProgressDialog.hide();
              Log.i(TAG, provider + " auth successful");
              setAuthenticatedUser(authData);
          }

          @Override
          public void onAuthenticationError(FirebaseError firebaseError) {
              mAuthProgressDialog.hide();
              showErrorDialog(firebaseError.toString());
          }
      }
  
      
      private void setAuthenticatedUser(AuthData authData) {
          if (authData != null) {
              /* Hide all the login buttons */
              fbLogin.setVisibility(View.GONE); 
              String name = null;
              if (authData.getProvider().equals("facebook")){
              name = (String)authData.getProviderData().get("displayName");
          		name = authData.getUid();
          		} else {
          			Log.e(TAG, "Invalid provider: " + authData.getProvider());
          		}
         
      	} else {
      		/* No authenticated user show all the login buttons */
      		fbLogin.setVisibility(View.VISIBLE);
      		
      	}

      	this.authData = authData;
      
      }
    private void onFacebookSessionStateChange(Session session, SessionState state, Exception exception) {
      	if (state.isOpened()) {
      		mAuthProgressDialog.show();
      		myFirebaseRef.authWithOAuthToken("facebook", session.getAccessToken(), new AuthResultHandler("facebook"));
      	} else if (state.isClosed()) {
      		/* Logged out of Facebook and currently authenticated with Firebase using Facebook, so do a logout */
      		if (this.authData != null && this.authData.getProvider().equals("facebook")) {
      			myFirebaseRef.unauth();
      			setAuthenticatedUser(null);
      		}
      		}
     }
    /*private void getWilsonInfo(String info){
    		String info = authData.AuthResultHandler.getProviderData.get("displayName");
    	
    	
    	
    	
    }*/
    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void makeGetRequest(){
    	HttpClient client = new DefaultHttpClient();
    	HttpGet request = new HttpGet("http://www.facebook.com/me/feed");
    	
    	HttpResponse response;
    	
    	try {
            response = client.execute(request);
             
            Log.d("Response of GET request", response.toString());
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
    }
 
    	
    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_class, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void postData(String data) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://user-model-prak.mybluemix.net/");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("content", data));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }

    private void makePostRequest() {
    	 
    	
        log.info("Ping");
        HttpClient httpClient = new DefaultHttpClient();
        log.info("Ping4");
                                // replace with your url
        HttpPost httpPost = new HttpPost("www.http://user-model-prak.mybluemix.net"); 
        log.info("Ping5");
 
        //Post Data
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
        nameValuePair.add(new BasicNameValuePair("content","I am happy to join with you today in what will go down in history as the greatest demonstration for freedom in the history of our nation. Five score years ago, a great American, in whose symbolic shadow we stand today, signed the Emancipation Proclamation. This momentous decree came as a great beacon light of hope to millions of Negro slaves who had been seared in the flames of withering injustice. It came as a joyous daybreak to end the long night of their captivity. But one hundred years later, the Negro still is not free. One hundred years later, the life of the Negro is still sadly crippled by the manacles of segregation and the chains of discrimination. One hundred years later, the Negro lives on a lonely island of poverty in the midst of a vast ocean of material prosperity. One hundred years later, the Negro is still languished in the corners of American society and finds himself an exile in his own land. And so weve come here today to dramatize a shameful condition. In a sense capital to cash a check. When the architects of our republic wrote the magnificent words of the Constitution and the Declaration of Independence, they were signing a promissory note to which every American was to fall heir. This note was a promise that all men, yes, black men as well as white men, would be guaranteed the unalienable Rights of Life, Liberty and the pursuit of Happiness. It is obvious today that America has defaulted on this promissory note, insofar as her citizens of color are concerned. Instead of honoring this sacred obligation, America has given the Negro people a bad check, a check which has come back marked"));
        log.info("Ping2");
        //Encoding POST data
        try {
        	log.info("Ping3");
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            // log exception
            e.printStackTrace();
        }
 
        //making POST request.
        try {
            HttpResponse response = httpClient.execute(httpPost);
            // write response to log
            Log.d("Http Post Response:", response.toString());
        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }
    }
}
