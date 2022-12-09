package com.example.demo.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.springframework.stereotype.Component;

/**
 * ★★★★★★★★★★Doma2★★★★★★★★★★
 * Entityサンプル
 * 
 * 【ポイント】
 *  =======================================================================
 *  ◆@Entityを付与（必須）
 *  ◆@Tableを付与し、name属性にテーブル名を指定（必須）
 *  ◆@Componentを付与（推奨）
 *  =======================================================================
 * @author yatty0812
 */
@Entity
@Table(name = "Customer")
@Component
public class CustomerEntity {

	/** ID */
	@Id
	@Column(name = "id")
	public Integer id;

	/** 名前 */
	@Column(name = "name")
	public String name;
}
