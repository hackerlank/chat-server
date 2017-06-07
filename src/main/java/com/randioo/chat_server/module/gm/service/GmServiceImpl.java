package com.randioo.chat_server.module.gm.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.randioo.chat_server.module.close.service.CloseService;
import com.randioo.randioo_server_base.cache.SessionCache;
import com.randioo.randioo_server_base.config.GlobleConfig;
import com.randioo.randioo_server_base.config.GlobleConfig.GlobleEnum;
import com.randioo.randioo_server_base.db.GameDB;
import com.randioo.randioo_server_base.platform.Platform;
import com.randioo.randioo_server_base.platform.Platform.OS;
import com.randioo.randioo_server_base.platform.SignalTrigger;
import com.randioo.randioo_server_base.scheduler.SchedulerManager;
import com.randioo.randioo_server_base.service.ObserveBaseService;
import com.randioo.randioo_server_base.template.EntityRunnable;
import com.randioo.randioo_server_base.template.Function;
import com.randioo.randioo_server_base.utils.StringUtils;

@Service("gmService")
public class GmServiceImpl extends ObserveBaseService implements GmService {

	@Autowired
	private SchedulerManager schedulerManager;

	@Autowired
	private CloseService closeService;

	@Override
	public void init() {

		Function function = new Function() {

			@Override
			public Object apply(Object... params) {
				GlobleConfig.set(GlobleEnum.LOGIN, false);

				System.out.println("port close");

				everybodyOffline();

				// 定时器全部停止
				try {
					schedulerManager.shutdown(1, TimeUnit.SECONDS);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				System.exit(0);

				return null;
			}

		};

		// 命令关闭信号
		try {
			logger.info(Platform.getOS().name());
			if (Platform.getOS() == OS.WIN)
				SignalTrigger.setSignCallback("INT", function);
			else if (Platform.getOS() == OS.LINUX)
				SignalTrigger.setSignCallback("ABRT", function);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (Platform.getOS() == OS.WIN) {
			new Thread(new EntityRunnable<Function>(function) {

				private Scanner in = new Scanner(System.in);

				@Override
				public void run(Function function) {
					while (true) {
						try {
							String command = in.nextLine();
							if (!StringUtils.isNullOrEmpty(command)) {
								if (command.equals("exit")) {
									function.apply();
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			}).start();

		}
	}

	/**
	 * 所有人下线
	 * 
	 * @author wcy 2016年12月9日
	 */
	private void everybodyOffline() {
		// 所有人下线
		Collection<IoSession> allSession = SessionCache.getAllSession();
		Iterator<IoSession> it = allSession.iterator();
		while (it.hasNext()) {
			it.next().close(true);
		}

	}

}
