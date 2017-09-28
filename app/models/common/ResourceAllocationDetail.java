package models.common;

import com.avaje.ebean.Model;
import models.framework_models.parent.IModel;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ResourceAllocationDetail extends Model implements IModel {

    public abstract Integer getYear();

    public abstract Integer getMonth();

    public abstract void setDays(Double days);

    public abstract Double getDays();
}
