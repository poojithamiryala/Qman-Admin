package com.example.poojithamiryala.admin_qma;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceUser;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

/**
 * Created by poojitha miryala on 10-03-2018.
 */

public class Azure
{
    public static MobileServiceClient mClient;
    public static MobileServiceTable<Signup_details> mToDoTable;
    public static MobileServiceTable<SignIn> mSignIn;
    public static MobileServiceUser user;
    public static MobileServiceTable<Organization_details> mOrgan;
    public static MobileServiceTable<AdminQ> madmin;
    public static MobileServiceTable<BookUser> mbook;
    public static int from;

}
