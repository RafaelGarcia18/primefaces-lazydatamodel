/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.uesocc.ingenieria.prn335_2018.web.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import sv.edu.uesocc.ingenieria.prn335_2018.datos.acceso.TipoVehiculoFacade;
import sv.edu.uesocc.ingenieria.prn335_2018.datos.acceso.TipoVehiculoFacadeLocal;
import sv.edu.uesocc.ingenieria.prn335_2018.flota.datos.definicion.TipoVehiculo;

/**
 *
 * @author rafael
 */
@Named(value = "tipoVehiculoBean")
@RequestScoped
public class TipoVehiculoBean implements Serializable {

    @EJB
    private TipoVehiculoFacadeLocal resource;

    private LazyDataModel<TipoVehiculo> lazyModel;
    private List<TipoVehiculo> dataSource;
    private TipoVehiculo selectedTipoVehiculo;

    @PostConstruct
    public void init() {
        dataSource = resource.findAll();
        this.lazyModel = new LazyDataModel<TipoVehiculo>() {
            @Override
            public List<TipoVehiculo> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                List<TipoVehiculo> data = new ArrayList<TipoVehiculo>();

                //filter
                for (TipoVehiculo car : dataSource) {
                    boolean match = true;

                    if (filters != null) {
                        for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                            try {
                                String filterProperty = it.next();
                                Object filterValue = filters.get(filterProperty);
                                String fieldValue = String.valueOf(car.getClass().getField(filterProperty).get(car));

                                if (filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                                    match = true;
                                } else {
                                    match = false;
                                    break;
                                }
                            } catch (Exception e) {
                                System.out.println("Error: "+e.getMessage());
                                match = false;
                            }
                        }
                    }

                    if (match) {
                        data.add(car);
                    }
                }

                //sort
                if (sortField != null) {
                    Collections.sort(data, new LazySorter(sortField, sortOrder));
                }

                //rowCount
                int dataSize = data.size();
                this.setRowCount(dataSize);

                //paginate
                if (dataSize > pageSize) {
                    try {
                        return data.subList(first, first + pageSize);
                    } catch (IndexOutOfBoundsException e) {
                        return data.subList(first, first + (dataSize % pageSize));
                    }
                } else {
                    return data;
                }
            }

            @Override
            public Object getRowKey(TipoVehiculo car) {
                return car.getIdTipoVehiculo();
            }

            @Override
            public TipoVehiculo getRowData(String rowKey) {
                for (TipoVehiculo car : dataSource) {
                    if (car.getIdTipoVehiculo().equals(rowKey)) {
                        return car;
                    }
                }
                return null;
            }

        };
    }

    public LazyDataModel<TipoVehiculo> getLazyModel() {
        return lazyModel;
    }

    public List<TipoVehiculo> getDataSource() {
        return dataSource;
    }

    public void setDataSource(List<TipoVehiculo> dataSource) {
        this.dataSource = dataSource;
    }

    public TipoVehiculo getSelectedTipoVehiculo() {
        return selectedTipoVehiculo;
    }

    public void setSelectedTipoVehiculo(TipoVehiculo selectedTipoVehiculo) {
        this.selectedTipoVehiculo = selectedTipoVehiculo;
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Car Selected", ((TipoVehiculo) event.getObject()).getIdTipoVehiculo().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}
