package com.barabashkastuff.urldownloader.dao;

import com.barabashkastuff.urldownloader.domain.Image;
import com.barabashkastuff.urldownloader.domain.status.ImageStatus;

import java.util.List;

/**
 * IImageDao Class
 *
 * @author a.slepakurov
 * @version 9/16/15
 */
public interface IImageDao {
    public String create(Image image);
    public void updateStatus(String id, ImageStatus status);
    public void updatePath(String id, String path);
    public void updateSize(String id, String size);
    public void updateWidth(String id, String width);
    public void updateHeight(String id, String height);
    public void updateContentType(String id, String contentType);
    public List<Image> getByRequestId(String requestId);
    public int removeByRequestId(String requestId);
}
