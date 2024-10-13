    package org.alongtheblue.alongtheblue_server.global.data.restaurant;

    import org.alongtheblue.alongtheblue_server.global.data.global.SimpleInformation;
    import org.alongtheblue.alongtheblue_server.global.data.search.SearchInformation;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;

    import java.util.List;
    import java.util.Optional;

    public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
        @Query("SELECT r FROM Restaurant r JOIN r.images i GROUP BY r HAVING COUNT(i) > 0")
        Page<SimpleInformation> findAllSimple(Pageable pageable);

        @Query("SELECT r FROM Restaurant r JOIN r.images i WHERE r.title LIKE %:title% GROUP BY r HAVING COUNT(i) > 0")
        Page<SearchInformation> findByTitleContaining(String title, Pageable pageable);

        Page<Restaurant> findAll(Pageable pageable);

        Optional<Restaurant> findByContentId(String contentId);
    }
