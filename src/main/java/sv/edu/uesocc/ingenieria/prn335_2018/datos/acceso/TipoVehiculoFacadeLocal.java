/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.uesocc.ingenieria.prn335_2018.datos.acceso;

import java.util.List;
import javax.ejb.Local;
import sv.edu.uesocc.ingenieria.prn335_2018.flota.datos.definicion.TipoVehiculo;

/**
 *
 * @author rafael
 */
@Local
public interface TipoVehiculoFacadeLocal {

    void create(TipoVehiculo tipoVehiculo);

    void edit(TipoVehiculo tipoVehiculo);

    void remove(TipoVehiculo tipoVehiculo);

    TipoVehiculo find(Object id);

    List<TipoVehiculo> findAll();

    List<TipoVehiculo> findRange(int[] range);

    int count();
    
}
