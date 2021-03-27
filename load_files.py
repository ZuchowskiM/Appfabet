import numpy as np
import json
import sys

np.set_printoptions(threshold=sys.maxsize)


def load_dictionary(path):
    with open(path, 'r') as JSON:
        sign_dict = json.load(JSON)
    sign_dict = {int(number): sign for number, sign in sign_dict.items()}
    return sign_dict


def load_files(load_path):
    signs = np.load(load_path + "\\" + "signs.npy")
    binarized_signs = np.load(load_path + "\\" + "binarized_signs.npy")
    labels = np.load(load_path + "\\" + "labels_int.npy")
    signs_dictionary = load_dictionary(load_path + "\\" + "dictionary.json")
    return signs, binarized_signs, labels, signs_dictionary

