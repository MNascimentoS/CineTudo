/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.database;

import java.sql.Connection;

/**
 *
 * @author José Júnior
 */
public interface Database {
    
    public Connection connect();
    public void desconnect(Connection conn);
}
