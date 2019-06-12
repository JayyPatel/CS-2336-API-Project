import twitter4j.Twitter;

import twitter4j.Trends;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public final class TwitterFetcher {
	// Declare necessary variables for the class
	
	public String top10[] = new String[10];
	
	public TwitterFetcher() throws TwitterException {
		// Set up the configuration for the twitter fetcher with the necessary Keys
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey("2ZXtBlc7EJgjHWhLbWHpnZFRc")
		.setOAuthConsumerSecret("pS7QgL0qnXSv1UDO4kuTdEmLpHXyfBromhf9t9VWrpr3D1BZEF")
		.setOAuthAccessToken("1062186278383484928-fUTGOEDwk5CWqdzDBOBweB2JaEqRf3")
		.setOAuthAccessTokenSecret("9H9EqYMVg7j8Ev6OTz0bW29RCZ8yJNUIqUkLy0oRSqfv1");
		
		// Use TwitterFactory to build a Twitter object
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		
		// Get the Trends for the US with WOEID 23424977
		Trends trends = twitter.getPlaceTrends(23424977);
		
		for(int i = 0; i < trends.getTrends().length; i++) {
			if(i == 10)
				break;
			top10[i] = trends.getTrends()[i].getName();
		}
			
	}
	
	public String[] getTop10() {
		// Return the top 10 trends
		return top10;
	}
	
	
}
