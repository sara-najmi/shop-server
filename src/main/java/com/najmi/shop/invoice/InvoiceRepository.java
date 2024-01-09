package com.najmi.shop.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query(value = "SELECT * fROM INVOICE WHERE USER_ID = :user_id", nativeQuery = true)
    List<Invoice> findAllByUserId(Integer user_id);


    @Query(value = "SELECT COUNT(ID) FROM INVOICE", nativeQuery = true)
    Integer countAll();

    @Query(value = "SELECT COUNT(ID) FROM INVOICE WHERE IS_DELIVERED = true", nativeQuery = true)
    Integer countAllDelivered();

    @Query(value = "SELECT COUNT(ID) FROM INVOICE WHERE IS_DELIVERED = false", nativeQuery = true)
    Integer countAllNotDelivered();


    @Query(value = "SELECT * FROM INVOICE ORDER BY ID DESC FETCH FIRST 10 ROWS ONLY ", nativeQuery = true)
    List<Invoice> findTop10();


    @Query(value = """
            SELECT SUBSTRING(g2j(created_date) FROM 6 FOR 2) AS MOUNTH, count(*) cnt, 'تیر' AS MONTH
            FROM invoice
            WHERE SUBSTRING(g2j(created_date) FROM 6 FOR 2) = '04'
            GROUP BY MOUNTH
            UNION ALL
            SELECT SUBSTRING(g2j(created_date) FROM 6 FOR 2) AS MOUNTH, count(*) cnt, 'مرداد' AS MONTH
            FROM invoice
            WHERE SUBSTRING(g2j(created_date) FROM 6 FOR 2) = '05'
            GROUP BY MOUNTH
            UNION ALL
            SELECT SUBSTRING(g2j(created_date) FROM 6 FOR 2) AS MOUNTH, count(*) cnt, 'شهریور' AS MONTH
            FROM invoice
            WHERE SUBSTRING(g2j(created_date) FROM 6 FOR 2) = '06'
            GROUP BY MOUNTH
            UNION ALL
            SELECT SUBSTRING(g2j(created_date) FROM 6 FOR 2) AS MOUNTH, count(*) cnt, 'مهر' AS MONTH
            FROM invoice
            WHERE SUBSTRING(g2j(created_date) FROM 6 FOR 2) = '07'
            GROUP BY MOUNTH
            union all
            SELECT SUBSTRING(g2j(created_date) FROM 6 FOR 2) AS MOUNTH, count(*) cnt, 'آبان' AS MONTH
            FROM invoice
            WHERE SUBSTRING(g2j(created_date) FROM 6 FOR 2) = '08'
            GROUP BY MOUNTH
            UNION ALL
            SELECT SUBSTRING(g2j(created_date) FROM 6 FOR 2) AS MOUNTH, count(*) , 'آذر' AS MONTH
            FROM invoice
            WHERE SUBSTRING(g2j(created_date) FROM 6 FOR 2) = '09'
            GROUP BY MOUNTH
            UNION ALL
            SELECT SUBSTRING(g2j(created_date) FROM 6 FOR 2) AS MOUNTH, count(*) cnt, 'دی' AS MONTH
            FROM invoice
            WHERE SUBSTRING(g2j(created_date) FROM 6 FOR 2) = '10'
            GROUP BY MOUNTH
            UNION ALL
            SELECT SUBSTRING(g2j(created_date) FROM 6 FOR 2) AS MOUNTH, count(*) cnt, 'بهمن' AS MONTH
            FROM invoice
            WHERE SUBSTRING(g2j(created_date) FROM 6 FOR 2) = '11'
            GROUP BY MOUNTH
            UNION ALL
            SELECT SUBSTRING(g2j(created_date) FROM 6 FOR 2) AS MOUNTH, count(*) cnt, 'اسفند' AS MONTH
            FROM invoice
            WHERE SUBSTRING(g2j(created_date) FROM 6 FOR 2) = '12'
            GROUP BY MOUNTH;
            """, nativeQuery = true)
    List<Object[]> chart();
}
