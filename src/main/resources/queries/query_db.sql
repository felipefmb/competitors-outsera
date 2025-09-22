select *
from       movie as mv
inner join movie_producer as mp on(mv.movie_id = mp.movie_id)
inner join movie_studio as ms on(mv.movie_id = ms.movie_id)
inner join producer as pr on(mp.producer_id = pr.producer_id)
inner join studio as st on(ms.studio_id = st.studio_id)