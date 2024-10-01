    package org.alongtheblue.alongtheblue_server.global.data.restaurant;

    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;

    public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
        @Query("SELECT r.contentId AS contentId, r.title AS title, r.addr AS address, r.images AS images FROM Restaurant r")
        Page<RestaurantSimpleInformation> findAllSimple(Pageable pageable);
    }
