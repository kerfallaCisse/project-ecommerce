package api.model;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class StatisticRepository implements PanacheRepository<Statistic>{}
