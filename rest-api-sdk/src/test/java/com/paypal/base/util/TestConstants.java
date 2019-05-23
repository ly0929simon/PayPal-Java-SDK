package com.paypal.base.util;

import com.paypal.base.rest.APIContext;

public class TestConstants {

   public static final String SANDBOX_CLIENT_ID = "AYSq3RDGsmBLJE-otTkBtM-jBRd1TCQwFf9RGfwddNXWz0uFU9ztymylOhRS";
   public static final String SANDBOX_CLIENT_SECRET = "EGnHDxD_qRPdaLdZz8iCr8N7_MzF-YHPTkjs6NKYQvQSBngp4PTTVWkPZRbL";
   public static final APIContext SANDBOX_CONTEXT = new APIContext(SANDBOX_CLIENT_ID, SANDBOX_CLIENT_SECRET, "sandbox");
}

