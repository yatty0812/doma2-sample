package com.example.demo.dao;


import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import com.example.demo.entity.CustomerEntity;


/**
 * ★★★★★★★★★★Doma2★★★★★★★★★★
 * Daoインタフェースサンプル
 * 
 * 【ポイント】
 *  =======================================================================
 *  ◆@Daoを付与（必須）
 *  ◆@ConfigAutowireableを付与（必須）
 *   ※このアノテーション付与により、Doma-Spring-Bootによる自動コンフィグを読み込ませることが出来る
 *     ちなみに、Doma-Spring-Bootの自動コンフィグが無い場合は、自前でコンフィグクラスを作成する必要がある
 *     
 *     ▼自動コンフィグクラス関連ソース
 *      org.seasar.doma.boot.autoconfigure.DomaAutoConfiguration
 *      org.seasar.doma.boot.autoconfigure.DomaConfig
 *      
 *    自動コンフィグクラスを適用すれば、
 *     ●コネクションプール  ・・・hikariCP採用　（※１）
 *     ●トランザクション制御・・・Spring標準機能で対応可能
 *    という構成となる
 *    
 *    ※１ 多分、
 *       org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration$PooledDataSourceConfiguration
 *       に付与された@Importアノテーションの、１個めがhikariCPだから、コレが採用されてると思われる！（多分）
 *  =======================================================================
 * @author yatty0812
 */
@Dao
@ConfigAutowireable
public interface CustomerDao {
	
	/**
	 * 単一SELECT
	 * 
	 * 【ポイント】
	 *  @Selectを付与（必須）
	 *  sqlファイルを別途作成（必須）
	 *  ▼sqlファイルの置き場
	 *    ＜法則＞ソースフォルダ/META-INF/Daoクラスフルパスディレクトリ/メソッド名.sql
	 *    ＜例＞　src/main/resources/META-INF/com/example/demo/dao/CustomerDao/getEntity.sql
	 */
	@Select
	CustomerEntity getEntity(Integer id); 
	
	/**
	 * INSERT
	 * 
	 * 【ポイント】
	 *  @Insertを付与（必須）
	 *  sqlファイルは作成不要（自動でやってくれる）
	 */
	@Insert
	int insert(CustomerEntity Customer);
	
	/**
	 * UPDATE
	 * 
	 * 【ポイント】
	 *  @Updateを付与（必須）
	 *  sqlファイルは作成不要（自動でやってくれる）
	 */
	@Update
	int update(CustomerEntity customer);

	/**
	 * INSERT
	 * 
	 * 【ポイント】
	 *  @Deleteを付与（必須）
	 *  sqlファイルは作成不要（自動でやってくれる）
	 */
	@Delete
	int delete(CustomerEntity customer);
}
