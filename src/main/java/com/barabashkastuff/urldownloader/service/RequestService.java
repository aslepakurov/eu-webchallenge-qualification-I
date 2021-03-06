package com.barabashkastuff.urldownloader.service;

import com.barabashkastuff.urldownloader.dao.IImageDao;
import com.barabashkastuff.urldownloader.dao.IRequestDao;
import com.barabashkastuff.urldownloader.domain.Image;
import com.barabashkastuff.urldownloader.domain.Request;
import com.barabashkastuff.urldownloader.domain.status.RequestStatus;
import com.barabashkastuff.urldownloader.worker.HtmlRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

/**
 * RequestService Class
 *
 * @author a.slepakurov
 * @version 9/16/15
 */
@Service
public class RequestService implements IRequestService {
    @Autowired
    private IRequestDao requestDao;
    @Autowired
    private IImageDao imageDao;
    @Autowired
    private ThreadPoolTaskExecutor asyncHtmlExecutor;

    public String create(Request request) {
        String id = requestDao.create(request);
        request.setId(id);
        asyncHtmlExecutor.submit(getHtmlRunner(request));
        return id;
    }

    @Override
    public void updateStatus(String id, RequestStatus requestStatus) {
        requestDao.updateStatus(id, requestStatus);
    }

    @Override
    public Request get(String id) {
        return requestDao.get(id);
    }

    public List<Image> getImages(String id) {
        return requestDao.allImagesDownloaded(id) ? imageDao.getByRequestId(id) : Collections.<Image>emptyList();
    }

    @Override
    public int remove(String id) {
        requestDao.remove(id);
        return imageDao.removeByRequestId(id);
    }

    private HtmlRunner getHtmlRunner(Request request) {
        return new HtmlRunner(requestDao, imageDao, request, asyncHtmlExecutor);
    }
}
