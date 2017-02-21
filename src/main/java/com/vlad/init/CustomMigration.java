package com.vlad.init;

import org.flywaydb.core.Flyway;

/**
 * Created by Fedyunkin_Vladislav on 2/3/2017.
 */
public class CustomMigration extends Flyway {

    public void repairAndMigrate(){
        this.repair();
        this.migrate();
    }

}
