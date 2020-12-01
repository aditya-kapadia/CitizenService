import io

import tensorflow as tf
import os
import numpy as np
import os, glob, cv2
import sys, argparse

from base64 import b64decode
import tensorflow as tf
from PIL import Image
from django.core.files.temp import NamedTemporaryFile
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from django.shortcuts import render

MAX_K = 3
#
# TF_GRAPH = "{base_path}/inception_model/graph.pb".format(
#     base_path=os.path.abspath(os.path.dirname(__file__)))
# TF_LABELS = "{base_path}/inception_model/labels.txt".format(
#     base_path=os.path.abspath(os.path.dirname(__file__)))


# def load_graph():
#     sess = tf.Session()
#     with tf.gfile.FastGFile(TF_GRAPH, 'rb') as tf_graph:
#         graph_def = tf.GraphDef()
#         graph_def.ParseFromString(tf_graph.read())
#         tf.import_graph_def(graph_def, name='')
#     label_lines = [line.rstrip() for line in tf.gfile.GFile(TF_LABELS)]
#     softmax_tensor = sess.graph.get_tensor_by_name('y_pred:0')
#     return sess, softmax_tensor, label_lines
#
#
# SESS, GRAPH_TENSOR, LABELS = load_graph()
trained_model = "C:/Users/adity/Desktop\image-classify-server-master\graph.pb".format(
     base_path=os.path.abspath(os.path.dirname(__file__)))
#@csrf_exempt
def load_graph(trained_model):
    with tf.gfile.GFile(trained_model, "rb") as f:
        graph_def = tf.GraphDef()
        graph_def.ParseFromString(f.read())

    with tf.Graph().as_default() as graph:
        tf.import_graph_def(
            graph_def,
            input_map=None,
            return_elements=None,
            name=""
        )
    return graph


@csrf_exempt
def classify_api(request):
    data = {"success": False}


    if request.method == "POST":
        tmp_f = NamedTemporaryFile()

        if request.FILES.get("image", None) is not None:
            image_request = request.FILES["image"]
            image_bytes = image_request.read()
            image = Image.open(io.BytesIO(image_bytes))
            image.save(tmp_f, image.format)
        elif request.POST.get("image64", None) is not None:
            base64_data = request.POST.get("image64", None).split(',', 1)[1]
            plain_data = b64decode(base64_data)
            tmp_f.write(plain_data)

        classify_result = tf_classify(tmp_f, int(request.POST.get('k', MAX_K)))
        tmp_f.close()

        if classify_result is not None:
            data["success"] = True
            data["confidence"] = {}
            for res in classify_result:
                data["confidence"][res[0]] = float(res[1])
            return JsonResponse(data)


def classify(request):
    return render(request, 'classify.html', {})


# noinspection PyUnresolvedReferences
# def tf_classify(image_file, k=MAX_K):
#     result = list()
#
#     image_data = tf.gfile.FastGFile(image_file.name, 'rb').read()
#
#     predictions = SESS.run(GRAPH_TENSOR, {'x:0': image_data})
#     predictions = predictions[0][:len(LABELS)]
#     top_k = predictions.argsort()[-k:][::-1]
#     for node_id in top_k:
#         label_string = LABELS[node_id]
#         score = predictions[node_id]
#         result.append([label_string, score])
#
#     return result


def tf_classify(image_file, k=MAX_K):
# First, pass the path of the image
# dir_path = os.path.dirname(("C:/Users/adity/Desktop/image-classify-server-master"))
# image_path = sys.argv[0]
# filename = dir_path + '/' + 'pothole.jpg'
    filename =image_file.name
    image_size = 160
    num_channels = 3
    images = []

    ##  Reading the image using OpenCV
    image = cv2.imread(filename)

    ##   Resizing the image to our desired size and preprocessing will be done exactly as done during training
    image = cv2.resize(image, (image_size, image_size), cv2.INTER_LINEAR)
    images.append(image)
    images = np.array(images, dtype=np.uint8)
    images = images.astype('float32')
    images = np.multiply(images, 1.0 / 255.0)
    ##   The input to the network is of shape [None image_size image_size num_channels].
    ## Hence we reshape.

    x_batch = images.reshape(1, image_size, image_size, num_channels)

    frozen_graph = "C:/Users/adity\Desktop\image-classify-server-master/graph.pb"
    label_path = "C:/Users/adity\Desktop\image-classify-server-master/labels.txt"


    labels = [line.rstrip() for line in tf.gfile.GFile(label_path)]

    with tf.gfile.GFile(frozen_graph, "rb") as f:
        graph_def = tf.GraphDef()
        graph_def.ParseFromString(f.read())

    with tf.Graph().as_default() as graph:
        tf.import_graph_def(graph_def,
                            input_map=None,
                            return_elements=None,
                            name=""
                            )
    ## NOW the complete graph with values has been restored
    y_pred = graph.get_tensor_by_name("y_pred:0")
    ## Let's feed the images to the input placeholders
    x = graph.get_tensor_by_name("x:0")
    y_test_images = np.zeros((1, 3))
    sess = tf.Session(graph=graph)
    ### Creating the feed_dict that is required to be fed to calculate y_pred
    feed_dict_testing = {x: x_batch}
    results = []
    result = sess.run(y_pred, feed_dict=feed_dict_testing)
    result = result[0][:k]
    top_k = result.argsort()[-k:][::-1]
    for node_id in top_k:
        label_string = labels[node_id]
        score = result[node_id]
        results.append([label_string, score])

        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        blur = cv2.GaussianBlur(gray, (5, 5), 0)

        out_final="Others"
        # contour detection
        if results[0][0] == "potholes":
            out_final=""
            (t, binary) = cv2.threshold(blur, 140, 255, cv2.THRESH_BINARY)
            # for pothole:120-150::::::for garbage:170-180:::::for multiple-potholes:140
            # cv2.imshow("black",blur)
            # find contours
            (_, contours, _) = cv2.findContours(binary, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)


            list = []
            for (i, c) in enumerate(contours):
                list.append(len(c))


            list.sort()
            #print(list[list.__len__() - 2]
            out_final +="potholes: "
            out_final += "Width="
            out_final +=str(list[list.__len__() - 2])
            out_final += "cm"
            if list[list.__len__() - 2] <70:
                out_final += " Depth=Shallow"
            elif list[list.__len__() - 2] <150:
                out_final += " Depth=Medium"
            else :
                out_final += " Depth=Deep"

        if results[0][0] == "sanitation":
            out_final = ""
            (t, binary) = cv2.threshold(blur, 165, 255, cv2.THRESH_BINARY)
            # for pothole:120-150::::::for garbage:170-180:::::for multiple-potholes:140
            # cv2.imshow("black",blur)
            # find contours
            (_, contours, _) = cv2.findContours(binary, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
            sum = 0
            for (i, c) in enumerate(contours):
                sum += len(c)
            out_final += "sanitation: "
            out_final += "Total objects="
            out_final += str(int(sum/27))


    print(out_final)
    out_f= []
    out_f.append([out_final, score])
    print(out_f)
    return out_f


