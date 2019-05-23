package com.paypal.base;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

@SuppressWarnings("deprecation")
public class ConfigManagerTest {

	@Test(dataProvider = "configParams", dataProviderClass = DataProviderClass.class)
	public void loadTest(ConfigManager conf) {
		Assert.assertEquals(conf.isPropertyLoaded(), true);
	}

	@Test(dataProvider = "configParams", dataProviderClass = DataProviderClass.class)
	public void getValuesByCategoryTest(ConfigManager conf) {
		Map<String, String> map = conf.getValuesByCategory("acct");
		Iterator<String> itr = map.keySet().iterator();
		while (itr.hasNext()) {
			assert (((String) itr.next()).contains("acct"));
		}
	}

	@Test(dataProvider = "configParams", dataProviderClass = DataProviderClass.class)
	public void getNumOfAcctTest(ConfigManager conf) {
		Set<String> set = conf.getNumOfAcct();
		Iterator<String> itr = set.iterator();
		while (itr.hasNext()) {
			assert (((String) itr.next()).contains("acct"));
		}
	}

}
