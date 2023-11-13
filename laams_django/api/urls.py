from django.urls import path
from photo_comparisons import views as photo_comparisons_views
from api import views as api_views
app_name = 'api'

urlpatterns = [
    path('v1/index', api_views.index, name='index'),
    path('v1/comparison', photo_comparisons_views.compare_photo, name='comparison'),
    ]