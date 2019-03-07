--
-- Created by IntelliJ IDEA.
-- User: czb123
-- Date: 2019/1/21
-- Time: 13:02
-- To change this template use File | Settings | File Templates.
--
local start = tonumber(ARGV[1])-1
-- 获取list中所有的元素
local all = redis.call('lrange', KEYS[1], 0, -1)
-- 读取start前面的额所有元素
redis.call('ltrim', KEYS[1], 0, start)
local len = #ARGV
-- 遍历推荐数据
for i = 2, len do
    local flag = false
    for j = 1, start+1 do
        if  ARGV[i] == all[j] then
            flag = true;
        end
    end
    -- 将未查看的数据存入redis
    if not flag then redis.call('rpush', KEYS[1], ARGV[i]) end
end
-- 遍历未查看数据
for i = start+2, #all do
    local flag = false
    for j = 2 , len do
        if  all[i] == ARGV[j] then
            flag = true
        end
    end
    -- 将去除推荐后的数据存入redis
    if not flag then redis.call('rpush', KEYS[1], all[i]) end
end
return 1

