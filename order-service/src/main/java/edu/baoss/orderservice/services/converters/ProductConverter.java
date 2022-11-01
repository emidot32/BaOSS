package edu.baoss.orderservice.services.converters;

import edu.baoss.orderservice.Constants;
import edu.baoss.orderservice.model.ProductEntity;
import edu.baoss.orderservice.model.dtos.ProductInstance;

public abstract class ProductConverter<Entity extends ProductEntity, Dto extends ProductInstance> {

    public abstract Dto entityToDto(Entity entity, boolean isShort);

    public abstract Entity dtoToEntity(Dto dto);

    public void setCommonParamsToDto(Dto dto, Entity entity) {
        dto.setInstanceId(entity.getInstance().getId());
        dto.setActivatedDateStr(entity.getInstance().getActivatedDate() == null
                ? "" : Constants.ONLY_DATE_FORMAT.format(entity.getInstance().getActivatedDate()));
        dto.setExpiredDate(entity.getInstance().getExpiredDate() == null
                ? "" : Constants.ONLY_DATE_FORMAT.format(entity.getInstance().getExpiredDate()));
        dto.setDisconnectedDateStr(entity.getInstance().getDisconnectedDate() == null
                ? "" : Constants.ONLY_DATE_FORMAT.format(entity.getInstance().getDisconnectedDate()));
        dto.setStatus(entity.getInstance().getStatus());
        dto.setUserId(entity.getInstance().getUserId());
    }


}
