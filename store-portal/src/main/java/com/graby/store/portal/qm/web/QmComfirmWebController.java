package com.graby.store.portal.qm.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.service.wms.ShipOrderService;
import com.graby.store.web.auth.ShiroContextUtils;


/**
 * 
 * 奇门确认WEBcontroller
 * @author shark_cj
 * 
 */

@Controller
@RequestMapping(value = "/store/entry/comfirm")
public class QmComfirmWebController {
	
	
	@Autowired
	private ShipOrderService shipOrderService;
	
	
	
	/**
	 * 显示待入库单据
	 */
	@RequestMapping(value = "entryOrderList")
	public String showEntryOrderList(Model model, ServletRequest request){
		Long userId = ShiroContextUtils.getUserid();
		
//		/** 入库单  */   ShipOrder
//		public static final String TYPE_ENTRY = "entry";
//		/** WAIT_STORAGE_RECEIVED ： 等待仓库接收 */
//		public static final String ENTRY_WAIT_STORAGE_RECEIVED = "ENTRY_WAIT_STORAGE_RECEIVED";
		/**
		 * 仓库无法确定
		 */
	    List<ShipOrder> orders  = shipOrderService.findEntryOrderOnWay();
		model.addAttribute("orders", orders);
		
		return "store/comfirm/entryOrderList";
		
	}
	
	
	/**
	 * 跳转到 编辑界面
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "entryOrderDetail/{id}")
	public String entryOrderDetail(@PathVariable("id") Long orderId, Model model) {
//		ShipOrder order =  
//		model.addAttribute("order", order);
		return "store/comfirm/entryOrderDetail";
	}
	
}
