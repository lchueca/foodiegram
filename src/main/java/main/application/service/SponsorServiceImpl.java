package main.application.service;

import main.domain.converter.PatrocinioConverter;
import main.domain.resource.PatrocinioResource;
import main.persistence.entity.Colaborador;
import main.persistence.entity.Patrocinio;
import main.persistence.repository.RepoColaborador;
import main.persistence.repository.RepoPatrocinio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class SponsorServiceImpl implements SponsorService {

    private PatrocinioConverter converterSponsor = new PatrocinioConverter();

    @Autowired
    private RepoPatrocinio repoSponsor;

    @Autowired
    private RepoColaborador repoColab;

    // devuelve la fecha de vencimiento del patrocinio de un nuevo sponsor
    public String getNewDateNewSponsor(Integer days) {
        LocalDate date = LocalDate.now();
        return date.plusDays(days).toString();
    }


    // devuelve la fecha de vencimiento del patrocinio de un sponsor antiguo
    // necesita que le pasen la fecha
    public String getNewDateOldSponsor(Integer days, LocalDate date) {
        return date.plusDays(days).toString();
    }


    // devuelve un patrocinio existente o null en caso contrario
    public PatrocinioResource getSponsorship(Integer id) { return converterSponsor.convert(repoSponsor.findById(id)); }


    // pone a true el valor de VIP y mete el dinero aportado
    public void setVIP(Integer id, Float money) {

        Colaborador colab = repoColab.findById(id);
        colab.setVip(true);
        colab.setMoney(colab.getMoney() + money);
        repoColab.save(colab);
    }


    // CREA/MODIFICA UN PATROCINIO
    //
    // crea un nuevo patrocinio
    // devuelve un patrocinio creado
    public PatrocinioResource obtain(Integer id, Integer type, Float money) {

        Patrocinio sponsorship = null;
        switch(type) {
            case 1: // 1 mes
                sponsorship = new Patrocinio(id, Date.valueOf(getNewDateNewSponsor(30)));
                break;
            case 2: // 3 meses
                sponsorship = new Patrocinio(id, Date.valueOf(getNewDateNewSponsor(30*3)));
                break;
            case 3: // 6 meses
                sponsorship = new Patrocinio(id, Date.valueOf(getNewDateNewSponsor(30*6)));
                break;
            case 4: // 12 meses
                sponsorship = new Patrocinio(id, Date.valueOf(getNewDateNewSponsor(30*12)));
                break;
        }
        repoSponsor.save(sponsorship);

        setVIP(id, money);

        return converterSponsor.convert(sponsorship);
    }


    // MODIFICAR PATROCINIO
    //
    // amplia el tiempo de patrocinio
    // devuelve un patrocinio modificado
    public PatrocinioResource modify(Integer id, Integer type, Float money) {

        Patrocinio sponsorship = repoSponsor.findById(id);
        LocalDate date = sponsorship.getEndtime().toLocalDate();
        switch(type) {
            case 1: // 1 mes
                sponsorship.setEndtime(Date.valueOf(date.plusDays(30)));
                break;
            case 2: // 3 meses
                sponsorship.setEndtime(Date.valueOf(date.plusDays(30*3)));
                break;
            case 3: // 6 meses
                sponsorship.setEndtime(Date.valueOf(date.plusDays(30*6)));
                break;
            case 4: // 12 meses
                sponsorship.setEndtime(Date.valueOf(date.plusDays(30*12)));
                break;
        }
        repoSponsor.save(sponsorship);

        setVIP(id, money);

        return converterSponsor.convert(sponsorship);
    }
}
