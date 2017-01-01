package com.advantage.accountsoap.config;

import com.advantage.accountsoap.util.BeansManager;

/**
 * Created by kubany on 12/28/2016.
 */
public interface Injectable {

    public void inject(BeansManager beansManager);
}
