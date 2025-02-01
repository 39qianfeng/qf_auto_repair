# -*- coding: utf-8 -*-

import selenium
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import time
from selenium.webdriver.chrome.service import Service
import pandas as pd
import os
import openpyxl
from openpyxl import Workbook

print(selenium.__version__)
print(1)
server = Service("C:\Program Files\Google\Chrome\Application\chromedriver-win64\chromedriver.exe")
driver = webdriver.Chrome(service=server)
# 打开B站热门视频排名页面
print(2)
driver.get('https://www.bilibili.com/v/popular/rank/all')
print(3)

# 等待页面加载完成
WebDriverWait(driver, 10).until(EC.presence_of_all_elements_located((By.CSS_SELECTOR, '.rank-item')))
print(4)

# 保存当前窗口句柄
current_window_handle = driver.current_window_handle

# 滚动到页面底部，为了加载出所有的html代码
# driver.execute_script("windows.scrollTo(0,document.body.scrollHeight);")

time.sleep(10)

list = driver.find_elements(By.CSS_SELECTOR, '.rank-item')
c = 0
data = [
    ['排行', '点赞', '投币数', '收藏数', '分享数']
]
for i in list:
    c += 1
    print(c)
    try:
        # 滚动到元素位置：如果元素不在可视区域内，可能需要先滚动到元素的位置,防止该链接被遮挡导致点击事件发送错误。
        driver.execute_script("arguments[0].scrollIntoView(true);", i)
        # 点击视频项中的<a>标签，进入视频详情页
        video_link = i.find_element(By.CSS_SELECTOR, 'a')
        video_link.click()
        
        # 等待新窗口出现
        WebDriverWait(driver, 10).until(EC.number_of_windows_to_be(2))
        
        # 切换到新窗口
        new_window_handle = [handle for handle in driver.window_handles if handle != current_window_handle][0]
        driver.switch_to.window(new_window_handle)
        
        # 等待新页面加载完成
        WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.XPATH, '/html/body/div[2]/div[2]/div[1]/div[3]/div[1]/div/div[4]/div/span/div/div/span')))
        
        # 这里可以进行新窗口中需要的操作
        print(driver.title)

        # WebDriverWait(5)
        # 获取点赞、投币、收藏和分享数量
        # 使用 XPath 定位元素
        like = driver.find_element(By.XPATH, '/html/body/div[2]/div[2]/div[1]/div[3]/div[1]/div/div[1]/div/span')
        coin = driver.find_element(By.XPATH, '/html/body/div[2]/div[2]/div[1]/div[3]/div[1]/div/div[2]/div/span')
        fav = driver.find_element(By.XPATH, '/html/body/div[2]/div[2]/div[1]/div[3]/div[1]/div/div[3]/div/span')
        share = driver.find_element(By.XPATH, '/html/body/div[2]/div[2]/div[1]/div[3]/div[1]/div/div[4]/div/span/div/div/span')
        print(like.text)
        print('排行:', c, '|点赞：', like.text, '|投币数:', coin.text, '|收藏数:', fav.text, '|分享数:', share.text)
        # 将当前视频的数据存入列表
        data.append([c,like.text,coin.text,fav.text,share.text])
        

        # 增加一些延迟，确保页面操作完成
        time.sleep(2)
        
        # 关闭新窗口
        driver.close()
        
        time.sleep(1)

        # 切换回原窗口
        driver.switch_to.window(current_window_handle)
        # 等待页面加载完成
        WebDriverWait(driver, 10).until(EC.presence_of_all_elements_located((By.XPATH,'/html/body/div[2]/div/div[2]/div[2]/ul/li[100]')))

    except Exception as e:
        print(f"Error on iteration {c}: {e}")
        driver.switch_to.window(current_window_handle)
        continue

print('结束')
# 关闭浏览器
driver.quit()

# 访问文件位置以及文件名称
data_dir = 'data'
# filename = 'data/B站视频排行榜数据.csv'
filename = 'data/B站视频排行榜数据.xlsx'

# 确保data目录存在
if not os.path.exists(data_dir):
    os.makedirs(data_dir)

# 保存数据
# 保存为.csv文件
# with open (filename,mode='w',newline='',encoding='utf-8') as file:
#     writer = csv.writer(file)
#     # 写入数据
#     for i in data:
#         writer.writerow(i)
# 创建一个新的工作簿

# 保存为.xlsx文件
wb = Workbook()
ws = wb.active  # 获取活动工作表

# 写入数据
for i in data:
    ws.append(i)

# 保存工作簿
wb.save(filename)