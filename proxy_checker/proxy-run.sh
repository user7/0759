for c in tinyproxy/proxy*conf
do
    tinyproxy -d -c "$c" &
done
