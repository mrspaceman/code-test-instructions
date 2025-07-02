package uk.co.droidinactu.tpximpacttask.urlshortener.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entity class representing a URL mapping.
 */
@Entity
@Table(name = "url_mappings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlMapping {

    /**
     * The alias/short identifier for the URL.
     * This is the primary key.
     */
    @Id
    @Column(nullable = false, unique = true)
    private String alias;

    /**
     * The original/full URL.
     */
    @Column(nullable = false)
    private String fullUrl;

    /**
     * The complete shortened URL.
     */
    @Column(nullable = false)
    private String shortUrl;

    /**
     * true indicates that this shortened url was created using a user supplied custom string.
     */
    @Column(nullable = false)
    private Boolean isCustomised = false;

    /**
     * The date and time when the URL mapping was created.
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        UrlMapping that = (UrlMapping) o;
        return getAlias() != null && Objects.equals(getAlias(), that.getAlias());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}