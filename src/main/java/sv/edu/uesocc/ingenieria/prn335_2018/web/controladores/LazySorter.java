/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.uesocc.ingenieria.prn335_2018.web.controladores;

import java.util.Comparator;
import org.primefaces.model.SortOrder;
import sv.edu.uesocc.ingenieria.prn335_2018.flota.datos.definicion.TipoVehiculo;
 
public class LazySorter implements Comparator<TipoVehiculo> {
 
    private String sortField;
     
    private SortOrder sortOrder;
     
    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 
    @Override
    public int compare(TipoVehiculo car1, TipoVehiculo car2) {
        try {
            Object value1 = TipoVehiculo.class.getField(this.sortField).get(car1);
            Object value2 = TipoVehiculo.class.getField(this.sortField).get(car2);
 
            int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            System.out.println("Error: "+e.getMessage());
            throw new RuntimeException();
        }
    }
}