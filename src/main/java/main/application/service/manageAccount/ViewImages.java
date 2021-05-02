package main.application.service.manageAccount;

import main.domain.resource.PublicacionResource;

import java.util.List;

public interface ViewImages {

    List<PublicacionResource> viewImages(Integer idUser);

    //futuro sprint: viewRatings(), viewComments();
}
